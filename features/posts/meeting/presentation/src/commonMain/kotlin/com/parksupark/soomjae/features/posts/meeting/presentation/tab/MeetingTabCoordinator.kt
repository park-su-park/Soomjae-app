package com.parksupark.soomjae.features.posts.meeting.presentation.tab

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.parksupark.soomjae.features.posts.common.presentation.PostAction
import org.koin.compose.viewmodel.koinViewModel

class MeetingTabCoordinator(
    private val onPostAction: (PostAction) -> Unit,
    private val viewModel: MeetingTabViewModel,
) {
    internal val screenStateFlow = viewModel.stateFlow
    internal val posts = viewModel.posts
    internal val events = viewModel.events

    fun handle(action: MeetingTabAction) {
        when (action) {
            is MeetingTabAction.OnClick -> { // Handle action
            }

            is MeetingTabAction.OnWritePostClick -> viewModel.handleWritePostClick()

            is MeetingTabAction.OnPostClick -> onPostAction(
                PostAction.NavigateToMeetingDetail(
                    action.postId,
                ),
            )

            is MeetingTabAction.OnPostLikeClick -> viewModel.handlePostLikeClick(action.postId)
        }
    }
}

@Composable
fun rememberMeetingTabCoordinator(
    onPostAction: (PostAction) -> Unit,
    viewModel: MeetingTabViewModel = koinViewModel(),
): MeetingTabCoordinator = remember(viewModel) {
    MeetingTabCoordinator(
        onPostAction = onPostAction,
        viewModel = viewModel,
    )
}
