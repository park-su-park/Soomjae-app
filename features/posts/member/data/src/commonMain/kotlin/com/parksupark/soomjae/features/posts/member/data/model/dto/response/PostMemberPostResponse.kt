package com.parksupark.soomjae.features.posts.member.data.model.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PostMemberPostResponse(
    @SerialName("memberPostId") val postId: Long,
)
