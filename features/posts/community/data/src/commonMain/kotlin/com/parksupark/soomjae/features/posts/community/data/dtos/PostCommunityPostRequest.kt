package com.parksupark.soomjae.features.posts.community.data.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PostCommunityPostRequest(
    val title: String,
    val content: String,
    @SerialName("category") val categoryId: Long,
)
