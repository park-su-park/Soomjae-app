package com.parksupark.soomjae.features.profile.presentation.profile.tabs.meeting_post

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.parksupark.soomjae.features.profile.presentation.profile.ProfileAction
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

class MeetingPostCoordinator(
    private val memberId: Long,
    private val onProfileAction: (ProfileAction) -> Unit,
    val viewModel: MeetingPostViewModel,
) {
    val screenStateFlow = viewModel.stateFlow

    fun handle(action: MeetingPostAction) {
        when (action) {
            MeetingPostAction.OnPullToRefresh -> viewModel.refreshPosts()
        }
    }
}

@Composable
fun rememberMeetingPostCoordinator(
    memberId: Long,
    onProfileAction: (ProfileAction) -> Unit,
    viewModel: MeetingPostViewModel = koinViewModel(
        key = "meeting_post_view_model_$memberId",
        parameters = {
            parametersOf(memberId)
        },
    ),
): MeetingPostCoordinator = remember(memberId, onProfileAction, viewModel) {
    MeetingPostCoordinator(
        memberId = memberId,
        onProfileAction = onProfileAction,
        viewModel = viewModel,
    )
}
