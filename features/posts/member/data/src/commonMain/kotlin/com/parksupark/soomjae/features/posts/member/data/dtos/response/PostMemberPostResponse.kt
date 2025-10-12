package com.parksupark.soomjae.features.posts.member.data.dtos.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostMemberPostResponse(
    @SerialName("memberPostId") val postId: Long,
)
