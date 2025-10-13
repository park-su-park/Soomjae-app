package com.parksupark.soomjae.features.posts.member.data.dtos.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LikeStatusResponse(
    @SerialName("liked") val isLiked: Boolean,
    @SerialName("likeCount") val likeCount: Int,
)
