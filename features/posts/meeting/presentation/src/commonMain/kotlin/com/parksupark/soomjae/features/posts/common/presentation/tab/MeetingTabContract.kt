package com.parksupark.soomjae.features.posts.common.presentation.tab

class MeetingTabState

sealed interface MeetingTabAction {
    data object OnClick : MeetingTabAction
}
