package com.parksupark.soomjae.core.domain.post.model

data class MeetingPostFilter(
    val categoryIds: List<Long> = emptyList(),
    val locationCodes: List<Long> = emptyList(),
    val recruitmentStatuses: List<RecruitmentStatus> = emptyList(),
)
