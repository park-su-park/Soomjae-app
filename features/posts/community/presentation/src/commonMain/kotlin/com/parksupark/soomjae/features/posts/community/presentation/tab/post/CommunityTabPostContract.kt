package com.parksupark.soomjae.features.posts.community.presentation.tab.post

data class CommunityTabPostState(
    val isLoading: Boolean = false,
    val isPostsRefreshing: Boolean = false,
)

sealed interface CommunityTabPostEvent {
    data object RefreshPosts : CommunityTabPostEvent

    data object NavigateToCommunityWrite : CommunityTabPostEvent
}
