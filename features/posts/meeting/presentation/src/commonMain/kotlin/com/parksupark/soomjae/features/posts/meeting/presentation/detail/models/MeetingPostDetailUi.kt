package com.parksupark.soomjae.features.posts.meeting.presentation.detail.models

import com.parksupark.soomjae.core.domain.post.model.MeetingPostDetail
import com.parksupark.soomjae.core.presentation.ui.post.model.CommentUi
import com.parksupark.soomjae.core.presentation.ui.post.model.MeetingPostUi
import com.parksupark.soomjae.core.presentation.ui.post.model.RecruitmentPeriodUi
import com.parksupark.soomjae.core.presentation.ui.post.model.toMeetingPostUi
import com.parksupark.soomjae.core.presentation.ui.post.model.toRecruitmentPeriodUi
import com.parksupark.soomjae.core.presentation.ui.post.model.toUi
import kotlin.time.ExperimentalTime
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class MeetingPostDetailUi(
    val post: MeetingPostUi,
    val isLike: Boolean,

    val maxParticipationCount: Int,
    val currentParticipantCount: Int,
    val isUserJoined: Boolean,
    val recruitmentPeriod: RecruitmentPeriodUi,

    val comments: ImmutableList<CommentUi>,
)

@OptIn(ExperimentalTime::class)
internal fun MeetingPostDetail.toMeetingPostDetailUi() = MeetingPostDetailUi(
    post = this.post.toMeetingPostUi(),
    isLike = this.post.isUserLiked,
    maxParticipationCount = this.maxParticipationCount,
    currentParticipantCount = this.currentParticipantCount,
    isUserJoined = this.isUserJoined,
    recruitmentPeriod = this.recruitmentPeriod.toRecruitmentPeriodUi(),
    comments = this.comments.map { it.toUi() }.toImmutableList(),
)
