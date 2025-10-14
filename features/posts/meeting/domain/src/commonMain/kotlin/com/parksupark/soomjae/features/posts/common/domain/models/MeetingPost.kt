package com.parksupark.soomjae.features.posts.common.domain.models

import com.parksupark.soomjae.core.domain.models.Member
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
data class MeetingPost(
    val id: Long,
    val title: String,
    val content: String,
    val createdAt: Instant,
    val author: Member,
    val category: Category?,
    val location: Location?,
    val likeCount: Int,
    val isUserLiked: Boolean,
    val maxParticipationCount: Int,
    val currentParticipantCount: Int,
    val recruitmentPeriod: RecruitmentPeriod,
)
