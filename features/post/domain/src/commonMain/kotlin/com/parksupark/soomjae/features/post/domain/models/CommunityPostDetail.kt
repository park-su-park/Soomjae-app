package com.parksupark.soomjae.features.post.domain.models

import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
data class CommunityPostDetail(
    val id: Long,
    val title: String,
    val content: String,
    val author: Member,
    val createdAt: Instant,
    val comments: List<Comment>,
)
