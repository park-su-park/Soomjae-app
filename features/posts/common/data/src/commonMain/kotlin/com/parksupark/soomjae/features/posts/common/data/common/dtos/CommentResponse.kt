package com.parksupark.soomjae.features.posts.common.data.common.dtos

import com.parksupark.soomjae.core.domain.post.model.Comment
import kotlin.time.ExperimentalTime
import kotlinx.datetime.Instant
import kotlinx.datetime.toStdlibInstant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommentResponse(
    @SerialName("commentId") val id: Long,
    @SerialName("content") val content: String,
    @SerialName("author") val author: MemberResponse,
    @SerialName("createdTime") val createdAt: Instant,
)

@OptIn(ExperimentalTime::class)
fun CommentResponse.toComment() = Comment(
    id = id,
    content = content,
    author = author.toModel(),
    createdAt = createdAt.toStdlibInstant(),
)
