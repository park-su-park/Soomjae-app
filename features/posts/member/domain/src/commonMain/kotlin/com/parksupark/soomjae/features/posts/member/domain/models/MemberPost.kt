package com.parksupark.soomjae.features.posts.member.domain.models

import com.parksupark.soomjae.core.domain.models.Member
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
data class MemberPost(
    val id: Long,
    val author: Member,
    val content: String,
    val images: List<String>,
    val createdAt: Instant,
    val isLiked: Boolean,
    val likeCount: Long,
    val commentCount: Long,
)
