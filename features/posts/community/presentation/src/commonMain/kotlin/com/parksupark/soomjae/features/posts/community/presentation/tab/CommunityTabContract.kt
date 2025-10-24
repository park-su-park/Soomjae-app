package com.parksupark.soomjae.features.posts.community.presentation.tab

import androidx.paging.PagingData
import com.parksupark.soomjae.features.posts.community.presentation.models.CommunityPostUi

internal data class CommunityTabState(
    val isLoading: Boolean = false,
    val isPostsRefreshing: Boolean = false,
    val posts: PagingData<CommunityPostUi> = PagingData.empty(),
    val canLikePost: Boolean = true,
)

internal sealed interface CommunityTabAction {
    data object OnPullToRefresh : CommunityTabAction

    data class OnRefreshChange(val isRefreshing: Boolean) : CommunityTabAction

    data class OnPostClick(val postId: Long) : CommunityTabAction

    data object OnCommunityWriteClick : CommunityTabAction
}

internal sealed interface CommunityTabEvent {
    data object RefreshPosts : CommunityTabEvent

    data object NavigateToCommunityWrite : CommunityTabEvent
}
