package com.parksupark.soomjae.features.posts.meeting.presentation.detail.models

import com.parksupark.soomjae.features.posts.common.presentation.models.CommentUi
import com.parksupark.soomjae.features.posts.meeting.presentation.tab.models.MeetingPostUi
import kotlinx.collections.immutable.ImmutableList

data class MeetingPostDetailUi(
    val post: MeetingPostUi,
    val isLike: Boolean,
    val comments: ImmutableList<CommentUi>,
)
