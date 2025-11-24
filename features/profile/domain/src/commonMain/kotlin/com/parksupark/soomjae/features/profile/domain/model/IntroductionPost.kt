package com.parksupark.soomjae.features.profile.domain.model

import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
data class IntroductionPost(
    val authorId: Long,
    val htmlContent: String,
    val lastModifiedAt: Instant,
)
