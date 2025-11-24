package com.parksupark.soomjae.features.profile.presentation.profile.tabs.meeting_post

data class MeetingPostState(
    val isRefreshing: Boolean = false,
)

sealed interface MeetingPostAction {
    data object OnPullToRefresh : MeetingPostAction

    data class OnClickPost(val postId: Long) : MeetingPostAction

    data class RefreshChange(val isRefreshing: Boolean) : MeetingPostAction
}

sealed interface MeetingPostEvent {
    data object RefreshPost : MeetingPostEvent
}
