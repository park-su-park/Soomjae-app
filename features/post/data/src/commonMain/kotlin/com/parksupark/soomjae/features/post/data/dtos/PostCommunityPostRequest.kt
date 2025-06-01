package com.parksupark.soomjae.features.post.data.dtos

import kotlinx.serialization.Serializable

@Serializable
internal data class PostCommunityPostRequest(
    val title: String,
    val content: String,
    val categoryId: Long,
)
