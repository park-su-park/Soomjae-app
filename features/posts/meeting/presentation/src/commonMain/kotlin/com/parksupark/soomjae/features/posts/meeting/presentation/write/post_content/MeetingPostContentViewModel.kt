package com.parksupark.soomjae.features.posts.meeting.presentation.write.post_content

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parksupark.soomjae.core.common.coroutines.SoomjaeDispatcher
import com.parksupark.soomjae.core.presentation.ui.errors.asUiText
import com.parksupark.soomjae.core.presentation.ui.utils.mapTextFieldState
import com.parksupark.soomjae.features.posts.common.domain.repositories.MeetingPostRepository
import com.parksupark.soomjae.features.posts.common.domain.usecase.ValidatePeriodUseCase
import com.parksupark.soomjae.features.posts.meeting.presentation.models.DateTimeRangeUi
import com.parksupark.soomjae.features.posts.meeting.presentation.models.MeetingCreationUi
import com.parksupark.soomjae.features.posts.meeting.presentation.models.mapper.toDateTimeRangeUi
import com.parksupark.soomjae.features.posts.meeting.presentation.models.mapper.toValidatePeriodParam
import com.parksupark.soomjae.features.posts.meeting.presentation.write.MeetingPostWriteAction
import com.parksupark.soomjae.features.posts.meeting.presentation.write.MeetingPostWriteAction.PeriodField.All
import com.parksupark.soomjae.features.posts.meeting.presentation.write.MeetingPostWriteAction.PeriodField.EndDate
import com.parksupark.soomjae.features.posts.meeting.presentation.write.MeetingPostWriteAction.PeriodField.EndTime
import com.parksupark.soomjae.features.posts.meeting.presentation.write.MeetingPostWriteAction.PeriodField.StartDate
import com.parksupark.soomjae.features.posts.meeting.presentation.write.MeetingPostWriteAction.PeriodField.StartTime
import com.parksupark.soomjae.features.posts.meeting.presentation.write.MeetingPostWriteEvent
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.toInstant

@OptIn(ExperimentalTime::class, ExperimentalCoroutinesApi::class)
class MeetingPostContentViewModel(
    private val dispatcher: SoomjaeDispatcher,
    private val meetingPostRepository: MeetingPostRepository,
    private val validatePeriodUseCase: ValidatePeriodUseCase,
) : ViewModel() {
    private val _stateFlow: MutableStateFlow<MeetingPostContentState> =
        MutableStateFlow(MeetingPostContentState())
    val stateFlow: StateFlow<MeetingPostContentState> = _stateFlow.asStateFlow()

    private val eventChannel = Channel<MeetingPostWriteEvent>()
    internal val events = eventChannel.receiveAsFlow()

    init {
        val titleFlow = _stateFlow.mapTextFieldState { it.inputTitle }
        val contentFlow = _stateFlow.mapTextFieldState { it.inputContent }
        val meetingFlow = _stateFlow.map { it.meeting }.distinctUntilChanged()
        val isSubmittingFlow = _stateFlow.map { it.isSubmitting }.distinctUntilChanged()

        combine(
            titleFlow,
            contentFlow,
            meetingFlow,
            isSubmittingFlow,
        ) { title, content, meeting, isSubmitting ->
            title.trim().isNotEmpty() &&
                content.trim().isNotEmpty() &&
                meeting != null &&
                !isSubmitting
        }.onEach { canSubmit ->
            _stateFlow.update { it.copy(canSubmit = canSubmit) }
        }.launchIn(viewModelScope)
    }

    fun submitPost() {
        if (!_stateFlow.value.canSubmit) return

        val meeting =
            _stateFlow.value.meeting ?: error("Meeting must be created before submitting a post.")
        val startTime = meeting.startDate.atTime(meeting.startTime)
        val endTime = if (meeting.endDate != null && meeting.endTime != null) {
            meeting.endDate.atTime(meeting.endTime)
        } else {
            error("End date and time must be set before submitting a post.")
        }
        val timeZone = TimeZone.currentSystemDefault()

        viewModelScope.launch(dispatcher.io) {
            _stateFlow.update { it.copy(isSubmitting = true) }

            meetingPostRepository.createPost(
                title = _stateFlow.value.inputTitle.text.toString().trim(),
                content = _stateFlow.value.inputContent.text.toString().trim(),
                categoryId = _stateFlow.value.selectedCategory?.id,
                locationCode = _stateFlow.value.selectedLocation?.code,
                startAt = startTime.toInstant(timeZone),
                endAt = endTime.toInstant(timeZone),
                maxParticipants = meeting.inputMaxParticipantCount.text.toString().toLongOrNull()
                    ?: 0L,
            ).fold(
                ifLeft = { failure ->
                    eventChannel.send(MeetingPostWriteEvent.OnPostCreateFailure(failure.asUiText()))
                },
                ifRight = {
                    eventChannel.send(MeetingPostWriteEvent.OnPostCreateSuccess(it.id))
                },
            )

            _stateFlow.update { it.copy(isSubmitting = false) }
        }
    }

    fun updateSelectedCategory(categoryId: Long) {
        _stateFlow.value = _stateFlow.value.copy(
            selectedCategory = _stateFlow.value.categories.find { it.id == categoryId },
        )
    }

    fun updateSelectedLocation(locationCode: Long) {
        _stateFlow.value = _stateFlow.value.copy(
            selectedLocation = _stateFlow.value.locations.find { it.code == locationCode },
        )
    }

    fun updateMeeting(meeting: MeetingCreationUi) {
        _stateFlow.update { it.copy(meeting = meeting) }
    }

    fun updateMeetingPeriod(isAllDay: Boolean) {
        _stateFlow.update { state ->
            val newPeriod = state.meetingForm.period.copy(isAllDay = isAllDay)
            val newMeeting = state.meetingForm.copy(period = newPeriod)
            state.copy(meetingForm = newMeeting)
        }
    }

    fun updateMeetingPeriod(
        newPeriod: DateTimeRangeUi,
        changedField: MeetingPostWriteAction.PeriodField,
    ) {
        val input = newPeriod.toValidatePeriodParam(
            changed = when (changedField) {
                StartDate, StartTime -> ValidatePeriodUseCase.ChangedField.START
                EndDate, EndTime -> ValidatePeriodUseCase.ChangedField.END
                All -> ValidatePeriodUseCase.ChangedField.START
            },
        )

        val result = validatePeriodUseCase(input)

        _stateFlow.update { state ->
            val newPeriod = result.toDateTimeRangeUi(isAllDay = newPeriod.isAllDay)
            val newMeeting = state.meetingForm.copy(period = newPeriod)

            state.copy(meetingForm = newMeeting)
        }
    }

    fun updateParticipantLimit(isLimited: Boolean) {
        _stateFlow.update { state ->
            val newParticipantLimit = state.meetingForm.participantLimit.copy(
                isLimited = isLimited,
            )
            state.copy(
                meetingForm = state.meetingForm.copy(
                    participantLimit = newParticipantLimit,
                ),
            )
        }
    }
}
