package com.parksupark.soomjae.features.post.presentation.post.tabs.community

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.parksupark.soomjae.features.post.presentation.post.PostAction
import org.koin.compose.viewmodel.koinViewModel

internal class CommunityTabCoordinator(
    private val onPostAction: (PostAction) -> Unit,
    private val viewModel: CommunityTabViewModel,
) {
    val screenStateFlow = viewModel.stateFlow
    val eventFlow = viewModel.eventChannel

    fun handle(action: CommunityTabAction) {
        when (action) {
            is CommunityTabAction.OnClick -> {
                // TODO
            }

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
