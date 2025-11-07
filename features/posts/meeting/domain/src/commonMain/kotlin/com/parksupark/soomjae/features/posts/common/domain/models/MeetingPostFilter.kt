package com.parksupark.soomjae.features.posts.common.domain.models

data class MeetingPostFilter(
    val categoryIds: List<Long> = emptyList(),
    val locationCodes: List<Long> = emptyList(),
    val recruitmentStatuses: List<RecruitmentStatus> = emptyList(),
)
