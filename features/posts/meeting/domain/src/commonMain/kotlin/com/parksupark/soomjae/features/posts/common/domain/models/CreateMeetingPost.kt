package com.parksupark.soomjae.features.posts.common.domain.models

data class CreateMeetingPost(
    val id: Long,
    val title: String,
    val content: String,
    val categoryId: Long?,
    val locationCode: Long?,
    val maxParticipationCount: Int?,
    val recruitmentPeriod: RecruitmentPeriod,
)
