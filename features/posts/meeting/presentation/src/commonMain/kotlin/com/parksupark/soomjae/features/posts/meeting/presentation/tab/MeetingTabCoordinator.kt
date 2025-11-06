package com.parksupark.soomjae.features.posts.meeting.presentation.tab

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewModelScope
import com.parksupark.soomjae.features.posts.common.presentation.PostAction
import com.parksupark.soomjae.features.posts.meeting.presentation.tab.post.MeetingTabPostViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import org.koin.compose.viewmodel.koinViewModel

class MeetingTabCoordinator(
    private val onPostAction: (PostAction) -> Unit,
    private val postViewModel: MeetingTabPostViewModel,
    private val viewmodelScope: CoroutineScope,
) {
    internal val screenStateFlow: StateFlow<MeetingTabState> =
        combine(postViewModel.stateFlow) { (postState) ->
            MeetingTabState(
                postState = postState,
            )
        }.stateIn(
            scope = viewmodelScope,
            started = SharingStarted.Eagerly,
            initialValue = MeetingTabState(),
        )
    internal val posts = postViewModel.posts
    internal val events = postViewModel.events

    fun handle(action: MeetingTabAction) {
        when (action) {
            is MeetingTabAction.OnClick -> { // Handle action
            }

            is MeetingTabAction.OnWritePostClick -> postViewModel.handleWritePostClick()

            is MeetingTabAction.OnPostClick -> onPostAction(
                PostAction.NavigateToMeetingDetail(
                    action.postId,
                ),
            )

            is MeetingTabAction.OnPostLikeClick -> postViewModel.handlePostLikeClick(action.postId)

            is MeetingTabAction.OnPullToRefresh -> postViewModel.refreshPost()

            is MeetingTabAction.RefreshChange -> postViewModel.setRefreshing(action.refresh)
        }
    }
}

@Composable
fun rememberMeetingTabCoordinator(
    onPostAction: (PostAction) -> Unit,
    postViewModel: MeetingTabPostViewModel = koinViewModel(),
): MeetingTabCoordinator = remember(postViewModel) {
    MeetingTabCoordinator(
        onPostAction = onPostAction,
        postViewModel = postViewModel,
        viewmodelScope = postViewModel.viewModelScope,
    )
}
