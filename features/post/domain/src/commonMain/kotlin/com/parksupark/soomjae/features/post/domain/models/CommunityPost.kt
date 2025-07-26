package com.parksupark.soomjae.features.post.domain.models

import com.parksupark.soomjae.core.domain.models.Member
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
data class CommunityPost(
    val id: Long,
    val title: String,
    val content: String,
    val author: Member,
    val createdAt: Instant,
)
