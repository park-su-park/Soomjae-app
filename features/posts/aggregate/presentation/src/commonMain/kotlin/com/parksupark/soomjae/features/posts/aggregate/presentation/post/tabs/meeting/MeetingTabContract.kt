package com.parksupark.soomjae.features.posts.aggregate.presentation.post.tabs.meeting

class MeetingTabState

sealed interface MeetingTabAction {
    data object OnClick : MeetingTabAction
}
