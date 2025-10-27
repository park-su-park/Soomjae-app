package com.parksupark.soomjae.features.posts.community.presentation.tab.post

data class CommunityTabPostState(
    val isLoading: Boolean = false,
    val isPostsRefreshing: Boolean = false,
)

sealed interface CommunityTabPostAction {
    data object OnPullToRefresh : CommunityTabPostAction

    data class OnRefreshChange(val isRefreshing: Boolean) : CommunityTabPostAction

    data class OnPostClick(val postId: Long) : CommunityTabPostAction

    data object OnCommunityWriteClick : CommunityTabPostAction
}

sealed interface CommunityTabPostEvent {
    data object RefreshPosts : CommunityTabPostEvent

    data object NavigateToCommunityWrite : CommunityTabPostEvent
}
