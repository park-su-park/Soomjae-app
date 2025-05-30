package com.parksupark.soomjae.features.post.data.dtos

import com.parksupark.soomjae.features.post.domain.models.CommunityPost
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class CommunityPostResponse(
    @SerialName("postId") val id: String,
    @SerialName("title") val title: String,
    @SerialName("content") val content: String,
    @SerialName("author") val author: MemberResponse,
    @SerialName("createdAt") val createdAt: Instant,
)

internal fun CommunityPostResponse.toModel(): CommunityPost = CommunityPost(
    id = id,
    title = title,
    content = content,
    author = author.toModel(),
    createdAt = createdAt,
)
