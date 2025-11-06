package com.parksupark.soomjae.features.posts.meeting.presentation.tab

import com.parksupark.soomjae.features.posts.meeting.presentation.tab.post.MeetingTabPostState

data class MeetingTabState(
    val postState: MeetingTabPostState = MeetingTabPostState(),
)

sealed interface MeetingTabAction {
    data object OnClick : MeetingTabAction

    data object OnWritePostClick : MeetingTabAction

    data class OnPostClick(val postId: Long) : MeetingTabAction

    data class OnPostLikeClick(val postId: Long) : MeetingTabAction

    data object OnPullToRefresh : MeetingTabAction

    data class RefreshChange(val refresh: Boolean) : MeetingTabAction
}

sealed interface MeetingTabEvent {
    data object NavigateToMeetingWrite : MeetingTabEvent

    data object RefreshPost : MeetingTabEvent
}
