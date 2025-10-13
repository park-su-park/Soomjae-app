package com.parksupark.soomjae.features.posts.meeting.presentation.detail.models

import com.parksupark.soomjae.features.posts.common.domain.models.MeetingPostDetail
import com.parksupark.soomjae.features.posts.common.presentation.models.CommentUi
import com.parksupark.soomjae.features.posts.common.presentation.models.toUi
import com.parksupark.soomjae.features.posts.meeting.presentation.tab.models.MeetingPostUi
import com.parksupark.soomjae.features.posts.meeting.presentation.tab.models.toMeetingPostUi
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
// TODO: implement likeCount and commentCount
internal fun MeetingPostDetail.toMeetingPostDetailUi() = MeetingPostDetailUi(
    post = this.post.toMeetingPostUi(),
    isLike = this.post.isUserLiked,
    maxParticipationCount = this.maxParticipationCount,
    currentParticipantCount = this.currentParticipantCount,
    isUserJoined = this.isUserJoined,
    recruitmentPeriod = this.recruitmentPeriod.toRecruitmentPeriodUi(),
    comments = this.comments.map { it.toUi() }.toImmutableList(),
)
