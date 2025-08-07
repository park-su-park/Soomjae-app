package com.parksupark.soomjae.features.posts.meeting.presentation.write

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parksupark.soomjae.core.presentation.ui.errors.asUiText
import com.parksupark.soomjae.core.presentation.ui.utils.collectAsFlow
import com.parksupark.soomjae.features.posts.common.domain.repositories.MeetingPostRepository
import com.parksupark.soomjae.features.posts.meeting.presentation.meetingcreate.MeetingCreateState
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.toInstant

@OptIn(ExperimentalTime::class)
class MeetingWriteViewModel(
    private val meetingPostRepository: MeetingPostRepository,
) : ViewModel() {
    private val _stateFlow: MutableStateFlow<MeetingWriteState> = MutableStateFlow(MeetingWriteState())
    internal val stateFlow: StateFlow<MeetingWriteState> = _stateFlow.asStateFlow()

    private val eventChannel = Channel<MeetingWriteEvent>()
    internal val events = eventChannel.receiveAsFlow()

    init {
        combine(
            _stateFlow.value.inputTitle.collectAsFlow(),
            _stateFlow.value.inputContent.collectAsFlow(),
            _stateFlow.distinctUntilChangedBy { it.meeting }.map { it.meeting },
        ) { (inputTitle, inputContent, meeting) ->
            val canSubmit = inputTitle.toString().trim().isNotBlank() &&
                inputContent.toString().trim().isNotBlank() &&
                meeting != null

            _stateFlow.update { it.copy(canSubmit = canSubmit) }
        }.launchIn(viewModelScope)
    }

    fun submitPost() {
        if (!_stateFlow.value.canSubmit) return

        val meeting = _stateFlow.value.meeting ?: error("Meeting must be created before submitting a post.")
        val startTime = meeting.startDate.atTime(meeting.startTime)
        val endTime = if (meeting.endDate != null && meeting.endTime != null) {
            meeting.endDate.atTime(meeting.endTime)
        } else {
            error("End date and time must be set before submitting a post.")
        }
        val timeZone = TimeZone.currentSystemDefault()

        viewModelScope.launch {
            meetingPostRepository.postPost(
                title = _stateFlow.value.inputTitle.text.toString().trim(),
                content = _stateFlow.value.inputContent.text.toString().trim(),
                categoryId = _stateFlow.value.selectedCategory?.id,
                locationCode = _stateFlow.value.selectedLocation?.code,
                startAt = startTime.toInstant(timeZone),
                endAt = endTime.toInstant(timeZone),
                maxParticipants = meeting.inputMaxParticipantCount.text.toString().toLongOrNull() ?: 0L,
            ).fold(
                ifLeft = { failure ->
                    eventChannel.send(MeetingWriteEvent.OnPostCreateFailure(failure.asUiText()))
                },
                ifRight = {
                    eventChannel.send(MeetingWriteEvent.OnPostCreateSuccess(it.id))
                },
            )
        }
    }

    fun selectCategory(categoryId: Long) {
        _stateFlow.value = _stateFlow.value.copy(
            selectedCategory = _stateFlow.value.categories.find { it.id == categoryId },
        )
    }

    fun selectLocation(locationCode: Long) {
        _stateFlow.value = _stateFlow.value.copy(
            selectedLocation = _stateFlow.value.locations.find { it.code == locationCode },
        )
    }

    // <editor-fold desc="MeetingCreate">
    private val _createStateFlow = MutableStateFlow(MeetingCreateState())
    internal val createStateFlow = _createStateFlow.asStateFlow()

    fun createMeeting() {
        _stateFlow.update { writeState ->
            writeState.copy(meeting = createStateFlow.value.meeting)
        }
    }

    fun updateStartDate(newStartDate: LocalDate) {
        _createStateFlow.update { state ->
            // update end date if it is before the new start date
            val newEndDate = state.meeting.endDate?.let { currentEndDate ->
                if (newStartDate > currentEndDate) newStartDate else currentEndDate
            } ?: state.meeting.endDate

            state.copy(
                meeting = state.meeting.copy(
                    startDate = newStartDate,
                    endDate = newEndDate,
                ),
            )
        }
    }

    fun updateStartTime(newStartTime: LocalTime) {
        _createStateFlow.update { state ->
            state.copy(
                meeting = state.meeting.copy(
                    startTime = newStartTime,
                ),
            )
        }
    }

    fun updateEndDate(newEndDate: LocalDate) {
        _createStateFlow.update { state ->
            state.copy(
                meeting = state.meeting.copy(
                    endDate = newEndDate,
                ),
            )
        }
    }

    fun updateEndTime(newEndTime: LocalTime) {
        _createStateFlow.update { state ->
            val startDateTime = state.meeting.startDate.atTime(state.meeting.startTime)
            val endDateTime = state.meeting.endDate?.atTime(newEndTime)

            val newStartTime = if (endDateTime != null && endDateTime > startDateTime) {
                state.meeting.startTime.minusHours(1)
            } else {
                state.meeting.startTime
            }

            state.copy(
                meeting = state.meeting.copy(
                    startTime = newStartTime,
                    endTime = newEndTime,
                ),
            )
        }
    }
    // </editor-fold>
}

private fun LocalTime.minusHours(hours: Int): LocalTime = LocalTime(
    hour = (this.hour - hours) % 24,
    minute = this.minute,
    second = this.second,
    nanosecond = this.nanosecond,
)
