package com.parksupark.soomjae.core.domain.post.model

import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
data class RecruitmentPeriod(
    val startTime: Instant,
    val endTime: Instant,
)
