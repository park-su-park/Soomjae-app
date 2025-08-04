package com.parksupark.soomjae.features.posts.meeting.presentation.tab

class MeetingTabState

sealed interface MeetingTabAction {
    data object OnClick : MeetingTabAction

    data object OnWritePostClick : MeetingTabAction
}

sealed interface MeetingTabEvent {
    data object NavigateToMeetingWrite : MeetingTabEvent
}
