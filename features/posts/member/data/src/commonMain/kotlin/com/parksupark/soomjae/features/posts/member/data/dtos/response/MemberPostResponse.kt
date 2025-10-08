package com.parksupark.soomjae.features.posts.member.data.dtos.response

import com.parksupark.soomjae.features.posts.common.data.common.dtos.MemberResponse
import com.parksupark.soomjae.features.posts.common.data.common.dtos.toModel
import com.parksupark.soomjae.features.posts.member.domain.models.MemberPost
import kotlin.time.ExperimentalTime
import kotlinx.datetime.Instant
import kotlinx.datetime.toStdlibInstant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// TODO: Add other fields as necessary
@Serializable
internal data class MemberPostResponse(
    @SerialName("id") val id: Long,
    @SerialName("content") val content: String,
    @SerialName("author") val author: MemberResponse,
    @SerialName("createdTime") val createdAt: Instant,
    @SerialName("isLiked") val isLiked: Boolean = false,
)

@OptIn(ExperimentalTime::class)
internal fun MemberPostResponse.toMemberPost(): MemberPost = MemberPost(
    id = this.id,
    content = this.content,
    author = this.author.toModel(),
    createdAt = this.createdAt.toStdlibInstant(),
    isLiked = this.isLiked,
)
