package com.parksupark.soomjae.features.posts.common.domain.models

import com.parksupark.soomjae.core.domain.post.model.RecruitmentPeriod

data class MeetingRecruitment(
    val maxParticipationCount: Int? = null,
    val recruitmentPeriod: RecruitmentPeriod,
)
