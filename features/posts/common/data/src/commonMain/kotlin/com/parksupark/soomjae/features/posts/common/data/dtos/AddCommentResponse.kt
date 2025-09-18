package com.parksupark.soomjae.features.posts.common.data.dtos

import com.parksupark.soomjae.features.posts.common.data.common.dtos.MemberResponse
import com.parksupark.soomjae.features.posts.common.data.common.dtos.toModel
import com.parksupark.soomjae.features.posts.common.domain.models.Comment
import kotlin.time.ExperimentalTime
import kotlinx.datetime.Instant
import kotlinx.datetime.toStdlibInstant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddCommentResponse(
    val commentId: Long,
    val content: String,
    val author: MemberResponse,
    @SerialName("createdTime") val createdAt: Instant,
)

@OptIn(ExperimentalTime::class)
fun AddCommentResponse.toComment(): Comment = Comment(
    id = commentId,
    content = content,
    author = author.toModel(),
    createdAt = createdAt.toStdlibInstant(),
)
