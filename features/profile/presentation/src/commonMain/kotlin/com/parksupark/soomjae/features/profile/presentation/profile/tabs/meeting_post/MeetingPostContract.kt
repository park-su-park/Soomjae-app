package com.parksupark.soomjae.features.profile.presentation.profile.tabs.meeting_post

data class MeetingPostState(
    val isRefreshing: Boolean = false,
)

sealed interface MeetingPostAction {
    data object OnPullToRefresh : MeetingPostAction
}
