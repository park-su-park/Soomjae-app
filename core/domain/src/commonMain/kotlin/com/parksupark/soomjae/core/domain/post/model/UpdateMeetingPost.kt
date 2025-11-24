package com.parksupark.soomjae.core.domain.post.model

data class UpdateMeetingPost(
    val id: Long,
    val title: String,
    val content: String,
    val categoryId: Long?,
    val locationCode: Long?,
    val maxParticipationCount: Int?,
    val recruitmentPeriod: RecruitmentPeriod,
)
