package com.parksupark.soomjae.core.domain.post.model

import com.parksupark.soomjae.core.domain.models.Member
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
data class Comment(
    val id: Long,
    val content: String,
    val author: Member,
    val createdAt: Instant,
)
