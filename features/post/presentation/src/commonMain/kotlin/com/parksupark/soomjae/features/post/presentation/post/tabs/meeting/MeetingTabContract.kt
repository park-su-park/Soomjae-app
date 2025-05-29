package com.parksupark.soomjae.features.post.presentation.post.tabs.meeting

class MeetingTabState

sealed interface MeetingTabAction {
    data object OnClick : MeetingTabAction
}
