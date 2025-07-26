package com.parksupark.soomjae.features.posts.community.domain.models

import com.parksupark.soomjae.core.domain.models.Member
import com.parksupark.soomjae.features.posts.common.domain.models.Comment
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
