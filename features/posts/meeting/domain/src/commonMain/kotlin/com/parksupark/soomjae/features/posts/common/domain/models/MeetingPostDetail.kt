package com.parksupark.soomjae.features.posts.common.domain.models

import kotlin.time.ExperimentalTime
import kotlin.time.Instant

data class MeetingPostDetail(
    val post: MeetingPost,

    val maxParticipationCount: Long,
    val currentParticipantCount: Long,
    val isUserJoined: Boolean,
    val recruitmentPeriod: RecruitmentPeriod,

    val comments: List<Comment>,
)

@OptIn(ExperimentalTime::class)
data class RecruitmentPeriod(
    val startTime: Instant,
    val endTime: Instant,
)
