package com.parksupark.soomjae.features.posts.community.presentation.tab

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.parksupark.soomjae.features.posts.common.presentation.PostAction
import org.koin.compose.viewmodel.koinViewModel

class CommunityTabCoordinator(
    private val onPostAction: (PostAction) -> Unit,
    private val viewModel: CommunityTabViewModel,
) {
    internal val screenStateFlow = viewModel.stateFlow
    internal val eventFlow = viewModel.eventChannel

    internal fun handle(action: CommunityTabAction) {
        when (action) {
            is CommunityTabAction.OnFavoriteClick -> {
                // TODO
            }

            is CommunityTabAction.OnPostClick -> onPostAction(PostAction.NavigateToCommunityDetail(postId = action.postId))

            is CommunityTabAction.OnCommunityWriteClick -> viewModel.handleCommunityWriteClick()

            is CommunityTabAction.OnPullToRefresh -> viewModel.refreshPosts()

            is CommunityTabAction.OnRefreshChange -> viewModel.setRefreshing(action.isRefreshing)
        }
    }
}

@Composable
internal fun rememberCommunityTabCoordinator(
    onPostAction: (PostAction) -> Unit,
    viewModel: CommunityTabViewModel = koinViewModel(),
): CommunityTabCoordinator = remember(viewModel) {
    CommunityTabCoordinator(
        onPostAction = onPostAction,
        viewModel = viewModel,
    )
}
