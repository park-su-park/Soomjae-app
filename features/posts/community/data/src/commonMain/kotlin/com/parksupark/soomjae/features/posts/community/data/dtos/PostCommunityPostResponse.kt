package com.parksupark.soomjae.features.posts.community.data.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PostCommunityPostResponse(
    @SerialName("postId") val id: Long,
)
