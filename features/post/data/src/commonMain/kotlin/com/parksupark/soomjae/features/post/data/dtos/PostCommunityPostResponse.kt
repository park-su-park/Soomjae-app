package com.parksupark.soomjae.features.post.data.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PostCommunityPostResponse(
    @SerialName("postId") val id: Long,
)
