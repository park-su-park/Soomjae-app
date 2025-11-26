package com.parksupark.soomjae.features.posts.common.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PostMeetingPostResponse(
    @SerialName("postId") val id: Long,
)
