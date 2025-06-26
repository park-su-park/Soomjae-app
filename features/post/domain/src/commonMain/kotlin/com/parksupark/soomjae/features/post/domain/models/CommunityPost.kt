package com.parksupark.soomjae.features.post.domain.models

import kotlinx.datetime.Instant

data class CommunityPost(
    val id: Long,
    val title: String,
    val content: String,
    val author: Member,
    val createdAt: Instant,
)
