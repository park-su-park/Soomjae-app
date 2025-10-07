package com.parksupark.soomjae.features.posts.common.presentation

sealed interface PostAction {
    data object OnClick : PostAction

    data class NavigateToCommunityDetail(val postId: Long) : PostAction

    data object NavigateToCommunityWrite : PostAction

    data object OnNavigateToMemberWrite : PostAction

    data class NavigateToMeetingDetail(val postId: Long) : PostAction

    data object NavigateToMemberWrite : PostAction
}
