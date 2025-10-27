package com.parksupark.soomjae.features.posts.community.presentation.tab

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.parksupark.soomjae.features.posts.common.presentation.PostAction
import com.parksupark.soomjae.features.posts.community.presentation.tab.post.CommunityTabPostAction
import com.parksupark.soomjae.features.posts.community.presentation.tab.post.CommunityTabPostViewModel
import org.koin.compose.viewmodel.koinViewModel

class CommunityTabCoordinator(
    private val onPostAction: (PostAction) -> Unit,
    private val viewModel: CommunityTabPostViewModel,
) {
    val screenStateFlow = viewModel.stateFlow
    val eventFlow = viewModel.eventChannel

    val posts = viewModel.posts

    internal fun handle(action: CommunityTabPostAction) {
        when (action) {
            is CommunityTabPostAction.OnPostClick -> onPostAction(
                PostAction.NavigateToCommunityDetail(
                    postId = action.postId,
                ),
            )

            is CommunityTabPostAction.OnCommunityWriteClick -> viewModel.handleCommunityWriteClick()

            is CommunityTabPostAction.OnPullToRefresh -> viewModel.refreshPosts()

            is CommunityTabPostAction.OnRefreshChange -> viewModel.setRefreshing(
                action.isRefreshing,
            )
        }
    }
}

@Composable
internal fun rememberCommunityTabCoordinator(
    onPostAction: (PostAction) -> Unit,
    viewModel: CommunityTabPostViewModel = koinViewModel(),
): CommunityTabCoordinator = remember(viewModel) {
    CommunityTabCoordinator(
        onPostAction = onPostAction,
        viewModel = viewModel,
    )
}
