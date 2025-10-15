package com.parksupark.soomjae.features.posts.meeting.presentation.tab

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class MeetingTabState(
    val isLikeSubmitting: ImmutableList<Long> = persistentListOf(),
)

sealed interface MeetingTabAction {
    data object OnClick : MeetingTabAction

    data object OnWritePostClick : MeetingTabAction

    data class OnPostClick(val postId: Long) : MeetingTabAction

    data class OnPostLikeClick(val postId: Long) : MeetingTabAction
}

sealed interface MeetingTabEvent {
    data object NavigateToMeetingWrite : MeetingTabEvent
}
