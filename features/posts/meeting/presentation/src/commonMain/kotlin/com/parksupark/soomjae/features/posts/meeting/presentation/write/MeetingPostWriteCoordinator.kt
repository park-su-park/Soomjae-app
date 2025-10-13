package com.parksupark.soomjae.features.posts.meeting.presentation.write

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parksupark.soomjae.features.posts.meeting.presentation.navigation.MeetingNavigator
import com.parksupark.soomjae.features.posts.meeting.presentation.write.creation.MeetingCreationAction
import com.parksupark.soomjae.features.posts.meeting.presentation.write.creation.MeetingCreationViewModel
import com.parksupark.soomjae.features.posts.meeting.presentation.write.post_content.MeetingPostContentViewModel
import com.parksupark.soomjae.features.posts.meeting.presentation.write.step.MeetingPostWriteStep
import com.parksupark.soomjae.features.posts.meeting.presentation.write.step.MeetingPostWriteStepViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class MeetingPostWriteCoordinator(
    private val navigator: MeetingNavigator,
    val stepViewModel: MeetingPostWriteStepViewModel,
    val contentViewModel: MeetingPostContentViewModel,
    val creationViewModel: MeetingCreationViewModel,
) : ViewModel() {
    internal val screenStateFlow = combine(
        stepViewModel.stateFlow,
        contentViewModel.stateFlow,
        creationViewModel.stateFlow,
    ) { stepState, contentState, creationState ->
        MeetingPostWriteCoordinatorState(
            stepState = stepState,
            contentState = contentState,
            creationState = creationState,
        )
    }.stateIn(
        scope = this.viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = MeetingPostWriteCoordinatorState(),
    )

    internal val events = contentViewModel.events

    internal fun handle(action: MeetingPostWriteAction) {
        when (action) {
            MeetingPostWriteAction.OnBackClick -> navigator.navigateBack()

            MeetingPostWriteAction.OnSubmitClick -> contentViewModel.submitPost()

            is MeetingPostWriteAction.OnCategorySelect -> contentViewModel.updateSelectedCategory(action.categoryId)

            is MeetingPostWriteAction.OnLocationSelect -> contentViewModel.updateSelectedLocation(action.locationCode)

            MeetingPostWriteAction.OnCreateMeetingPostClick -> stepViewModel.setScreenState(MeetingPostWriteStep.CREATE)
        }
    }

    internal fun handle(action: MeetingCreationAction) {
        when (action) {
            MeetingCreationAction.OnBackClick -> stepViewModel.setScreenState(MeetingPostWriteStep.CONTENT)

            MeetingCreationAction.OnCreateClick -> {
                val meeting = creationViewModel.createMeeting()
                contentViewModel.updateMeeting(meeting)
                stepViewModel.setScreenState(MeetingPostWriteStep.CONTENT)
            }

            is MeetingCreationAction.OnStartDateSelected -> creationViewModel.updateStartDate(action.startDate)

            is MeetingCreationAction.OnStartTimeSelected -> creationViewModel.updateStartTime(action.startTime)

            is MeetingCreationAction.OnEndDateSelected -> creationViewModel.updateEndDate(action.endDate)

            is MeetingCreationAction.OnEndTimeSelected -> creationViewModel.updateEndTime(action.endTime)
        }
    }
}
