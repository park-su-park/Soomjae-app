package com.parksupark.soomjae.features.posts.meeting.presentation.meetingcreate

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.parksupark.soomjae.features.posts.meeting.presentation.navigation.MeetingNavigator
import com.parksupark.soomjae.features.posts.meeting.presentation.write.MeetingWriteViewModel
import org.koin.compose.viewmodel.koinViewModel

class MeetingCreateCoordinator(
    private val navigator: MeetingNavigator,
    private val viewModel: MeetingWriteViewModel,
) {
    internal val screenStateFlow = viewModel.createStateFlow

    internal fun handle(action: MeetingCreateAction) {
        when (action) {
            MeetingCreateAction.OnBackClick -> navigator.navigateToMeetingWrite()

            MeetingCreateAction.OnCreateClick -> {
                viewModel.createMeeting()
                navigator.navigateBack()
            }

            is MeetingCreateAction.OnStartDateSelected -> viewModel.updateStartDate(action.startDate)

            is MeetingCreateAction.OnStartTimeSelected -> viewModel.updateStartTime(action.startTime)

            is MeetingCreateAction.OnEndDateSelected -> viewModel.updateEndDate(action.endDate)

            is MeetingCreateAction.OnEndTimeSelected -> viewModel.updateEndTime(action.endTime)
        }
    }
}

@Composable
fun rememberMeetingCreateCoordinator(
    navigator: MeetingNavigator,
    viewModel: MeetingWriteViewModel = koinViewModel(),
): MeetingCreateCoordinator = remember(viewModel) {
    MeetingCreateCoordinator(
        navigator = navigator,
        viewModel = viewModel,
    )
}
