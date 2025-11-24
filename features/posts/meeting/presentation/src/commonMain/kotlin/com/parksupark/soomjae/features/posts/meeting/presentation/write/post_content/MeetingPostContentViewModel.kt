package com.parksupark.soomjae.features.posts.meeting.presentation.write.post_content

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.parksupark.soomjae.core.common.coroutines.SoomjaeDispatcher
import com.parksupark.soomjae.core.common.utils.fold
import com.parksupark.soomjae.core.domain.post.repository.MeetingPostRepository
import com.parksupark.soomjae.core.presentation.ui.errors.asUiText
import com.parksupark.soomjae.core.presentation.ui.utils.mapTextFieldState
import com.parksupark.soomjae.features.posts.common.domain.repositories.CategoryRepository
import com.parksupark.soomjae.features.posts.common.domain.repositories.LocationRepository
import com.parksupark.soomjae.features.posts.common.domain.usecase.GetMeetingPostForEditUseCase
import com.parksupark.soomjae.features.posts.common.domain.usecase.UpdateMeetingPostUseCase
import com.parksupark.soomjae.features.posts.common.domain.usecase.ValidatePeriodUseCase
import com.parksupark.soomjae.features.posts.common.presentation.models.toLocationUi
import com.parksupark.soomjae.features.posts.common.presentation.models.toUi
import com.parksupark.soomjae.features.posts.meeting.presentation.models.DateTimeRangeUi
import com.parksupark.soomjae.features.posts.meeting.presentation.models.fromEditMeeting
import com.parksupark.soomjae.features.posts.meeting.presentation.models.mapper.toDateTimeRangeUi
import com.parksupark.soomjae.features.posts.meeting.presentation.models.mapper.toValidatePeriodParam
import com.parksupark.soomjae.features.posts.meeting.presentation.navigation.MeetingDestination
import com.parksupark.soomjae.features.posts.meeting.presentation.write.MeetingPostWriteAction
import com.parksupark.soomjae.features.posts.meeting.presentation.write.MeetingPostWriteAction.PeriodField.All
import com.parksupark.soomjae.features.posts.meeting.presentation.write.MeetingPostWriteAction.PeriodField.EndDate
import com.parksupark.soomjae.features.posts.meeting.presentation.write.MeetingPostWriteAction.PeriodField.EndTime
import com.parksupark.soomjae.features.posts.meeting.presentation.write.MeetingPostWriteAction.PeriodField.StartDate
import com.parksupark.soomjae.features.posts.meeting.presentation.write.MeetingPostWriteAction.PeriodField.StartTime
import com.parksupark.soomjae.features.posts.meeting.presentation.write.MeetingPostWriteEvent
import com.parksupark.soomjae.features.posts.meeting.presentation.write.post_content.mapper.toCreateMeetingPost
import kotlin.time.ExperimentalTime
import kotlinx.collections.immutable.toPersistentList
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
    savedStateHandle: SavedStateHandle,
    private val dispatcher: SoomjaeDispatcher,
    private val meetingPostRepository: MeetingPostRepository,
    private val categoryRepository: CategoryRepository,
    private val locationRepository: LocationRepository,
    private val validatePeriodUseCase: ValidatePeriodUseCase,
    private val getMeetingPostForEditUseCase: GetMeetingPostForEditUseCase,
    private val updateMeetingPostUseCase: UpdateMeetingPostUseCase,
) : ViewModel() {
    private val postId: Long? = savedStateHandle.toRoute<MeetingDestination.MeetingWrite>().postId
    private val writeMode = if (postId == null) WriteMode.CREATE else WriteMode.EDIT

    private val _stateFlow: MutableStateFlow<MeetingPostContentState> =
        MutableStateFlow(MeetingPostContentState())
    val stateFlow: StateFlow<MeetingPostContentState> = _stateFlow.asStateFlow()

    private val eventChannel = Channel<MeetingPostWriteEvent>()
    internal val events = eventChannel.receiveAsFlow()

    init {
        loadInitialData()
        initInputValidation()
    }

    private fun loadInitialData() {
        val timeZone = TimeZone.currentSystemDefault()

        viewModelScope.launch(dispatcher.io) {
            categoryRepository.getAllCategories().fold(
                ifLeft = { failure ->
                    eventChannel.send(
                        MeetingPostWriteEvent.ShowErrorToast(failure.asUiText()),
                    )
                },
                ifRight = { categories ->
                    _stateFlow.update { state ->
                        state.copy(
                            categories = categories.values.map { it.toUi() }.toPersistentList(),
                        )
                    }
                },
            )
        }

        viewModelScope.launch(dispatcher.io) {
            locationRepository.getAllLocations().fold(
                ifLeft = { failure ->
                    eventChannel.send(
                        MeetingPostWriteEvent.ShowErrorToast(failure.asUiText()),
                    )
                },
                ifRight = { locations ->
                    _stateFlow.update { state ->
                        state.copy(
                            locations = locations.map { it.toLocationUi() }.toPersistentList(),
                        )
                    }
                },
            )
        }

        if (writeMode == WriteMode.EDIT && postId != null) {
            viewModelScope.launch(dispatcher.io) {
                getMeetingPostForEditUseCase(postId).fold(
                    ifLeft = {
                        eventChannel.send(
                            MeetingPostWriteEvent.ShowErrorToast(it.asUiText()),
                        )
                    },
                    ifRight = {
                        _stateFlow.update { state ->
                            state.copy(
                                inputTitle = TextFieldState(initialText = it.title),
                                inputContent = TextFieldState(initialText = it.content),
                                selectedCategory = it.category?.toUi(),
                                selectedLocation = it.location?.toLocationUi(),
                                meetingForm = fromEditMeeting(
                                    editMeeting = it,
                                    timeZone = timeZone,
                                ),
                            )
                        }
                    },
                )
            }
        }
    }

    private fun initInputValidation() {
        val titleFlow = _stateFlow.mapTextFieldState { it.inputTitle }
        val contentFlow = _stateFlow.mapTextFieldState { it.inputContent }
        val isSubmittingFlow = _stateFlow.map { it.isSubmitting }.distinctUntilChanged()

        combine(
            titleFlow,
            contentFlow,
            isSubmittingFlow,
        ) { title, content, isSubmitting ->
            title.trim().isNotEmpty() &&
                content.trim().isNotEmpty() &&
                !isSubmitting
        }.onEach { canSubmit ->
            _stateFlow.update { it.copy(canSubmit = canSubmit) }
        }.launchIn(viewModelScope)
    }

    fun submitPost() {
        if (!_stateFlow.value.canSubmit) return

        val timeZone = TimeZone.currentSystemDefault()

        if (writeMode == WriteMode.CREATE) {
            viewModelScope.launch(dispatcher.io) {
                _stateFlow.update { it.copy(isSubmitting = true) }
                val state = _stateFlow.value
                val meeting = state.meetingForm

                meetingPostRepository.createPost(
                    title = state.inputTitle.text.toString().trim(),
                    content = state.inputContent.text.toString().trim(),
                    categoryId = state.selectedCategory?.id,
                    locationCode = state.selectedLocation?.code,
                    startAt = meeting.period.startDate
                        .atTime(meeting.period.startTime)
                        .toInstant(timeZone),
                    endAt = meeting.period.endDate
                        .atTime(meeting.period.endTime)
                        .toInstant(timeZone),
                    maxParticipants = if (meeting.participantLimit.isLimited) {
                        meeting.participantLimit.limitCount.text.toString().toLongOrNull()
                    } else {
                        null
                    },
                ).fold(
                    ifLeft = { failure ->
                        eventChannel.send(MeetingPostWriteEvent.ShowErrorToast(failure.asUiText()))
                    },
                    ifRight = {
                        eventChannel.send(MeetingPostWriteEvent.OnPostCreateSuccess(it.id))
                    },
                )

                _stateFlow.update { it.copy(isSubmitting = false) }
            }
        } else if (writeMode == WriteMode.EDIT && postId != null) {
            viewModelScope.launch(dispatcher.io) {
                _stateFlow.update { it.copy(isSubmitting = true) }

                val updatedPost = _stateFlow.value.toCreateMeetingPost(postId, timeZone)
                updateMeetingPostUseCase(updatedPost).fold(
                    ifLeft = { failure ->
                        eventChannel.send(MeetingPostWriteEvent.ShowErrorToast(failure.asUiText()))
                    },
                    ifRight = {
                        eventChannel.send(MeetingPostWriteEvent.NavigateToMeetingPostDetail(postId))
                    },
                    finally = {
                        _stateFlow.update { it.copy(isSubmitting = false) }
                    },
                )
            }
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

    fun updateParticipantLimit(isUnlimited: Boolean) {
        _stateFlow.update { state ->
            val newParticipantLimit = state.meetingForm.participantLimit.copy(
                isLimited = !isUnlimited,
            )
            state.copy(
                meetingForm = state.meetingForm.copy(
                    participantLimit = newParticipantLimit,
                ),
            )
        }
    }
}
