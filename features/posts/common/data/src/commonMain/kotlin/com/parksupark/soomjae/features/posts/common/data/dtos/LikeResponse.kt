package com.parksupark.soomjae.features.posts.common.data.dtos

import com.parksupark.soomjae.features.posts.common.domain.models.Like
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class LikeResponse(
    @SerialName("liked") val liked: Boolean,
    @SerialName("likeCount") val likeCount: Long,
)

internal fun LikeResponse.toDomain(): Like = Like(
    liked = liked,
    likeCount = likeCount,
)
