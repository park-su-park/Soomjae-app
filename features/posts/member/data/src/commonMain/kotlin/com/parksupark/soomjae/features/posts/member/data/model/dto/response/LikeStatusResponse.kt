package com.parksupark.soomjae.features.posts.member.data.model.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class LikeStatusResponse(
    @SerialName("liked") val isLiked: Boolean,
    @SerialName("likeCount") val likeCount: Int,
)
