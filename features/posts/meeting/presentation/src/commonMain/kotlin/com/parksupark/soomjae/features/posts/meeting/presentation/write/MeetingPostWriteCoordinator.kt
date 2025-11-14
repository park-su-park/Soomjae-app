package com.parksupark.soomjae.features.posts.meeting.presentation.write

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.parksupark.soomjae.features.posts.meeting.presentation.navigation.MeetingNavigator
import com.parksupark.soomjae.features.posts.meeting.presentation.write.post_content.MeetingPostContentViewModel
import org.koin.compose.viewmodel.koinViewModel

class MeetingPostWriteCoordinator(
    private val navigator: MeetingNavigator,
    val viewModel: MeetingPostContentViewModel,
) {
    internal val screenStateFlow = viewModel.stateFlow

    internal val events = viewModel.events

    internal fun handle(action: MeetingPostWriteAction) {
        when (action) {
            MeetingPostWriteAction.OnBackClick -> navigator.navigateBack()

            MeetingPostWriteAction.OnSubmitClick -> viewModel.submitPost()

            is MeetingPostWriteAction.OnCategorySelect ->
                viewModel.updateSelectedCategory(action.categoryId)

            is MeetingPostWriteAction.OnLocationSelect ->
                viewModel.updateSelectedLocation(action.locationCode)

            is MeetingPostWriteAction.OnParticipantLimitToggled ->
                viewModel.updateParticipantLimit(isUnlimited = action.isUnlimited)

            is MeetingPostWriteAction.OnAllDayToggled ->
                viewModel.updateMeetingPeriod(isAllDay = action.isAllDay)

            is MeetingPostWriteAction.OnMeetingPeriodChanged ->
                viewModel.updateMeetingPeriod(
                    newPeriod = action.period,
                    changedField = action.changedField,
                )
        }
    }

    fun navigateToMeetingPostDetail(postId: Long) {
        navigator.navigateToMeetingDetail(postId)
    }
}

@Composable
fun rememberMeetingPostWriteCoordinator(
    navigator: MeetingNavigator,
    contentViewModel: MeetingPostContentViewModel = koinViewModel(),
) = remember(navigator, contentViewModel) {
    MeetingPostWriteCoordinator(
        navigator = navigator,
        viewModel = contentViewModel,
    )
}
