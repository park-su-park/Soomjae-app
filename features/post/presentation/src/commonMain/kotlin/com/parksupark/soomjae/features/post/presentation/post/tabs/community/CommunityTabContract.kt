package com.parksupark.soomjae.features.post.presentation.post.tabs.community

import androidx.paging.PagingData
import com.parksupark.soomjae.features.post.presentation.post.models.CommunityPostUi

internal data class CommunityTabState(
    val isLoading: Boolean = false,
    val isPostsRefreshing: Boolean = false,
    val posts: PagingData<CommunityPostUi> = PagingData.empty(),
)

sealed interface CommunityTabAction {
    data object OnClick : CommunityTabAction

    data object OnPullToRefresh : CommunityTabAction

    data class OnRefreshChange(val isRefreshing: Boolean) : CommunityTabAction

    data class OnFavoriteClick(val postId: String) : CommunityTabAction

    data class OnPostClick(val postId: String) : CommunityTabAction
}

sealed interface CommunityTabEvent {
    data object RefreshPosts : CommunityTabEvent
}
