package com.parksupark.soomjae.features.posts.meeting.presentation.tab.post

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class MeetingTabPostState(
    val isLikeSubmitting: ImmutableList<Long> = persistentListOf(),
    val isPostsRefreshing: Boolean = false,
)

sealed interface MeetingTabPostEvent {
    data object NavigateToMeetingWrite : MeetingTabPostEvent

    data object RefreshPost : MeetingTabPostEvent

    data object PostCreated : MeetingTabPostEvent
}
