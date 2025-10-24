package com.parksupark.soomjae.features.posts.community.domain.model

import com.parksupark.soomjae.features.posts.common.domain.models.Comment
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
data class CommunityPostDetail(
    val post: CommunityPost,
    val comments: List<Comment>,
)
