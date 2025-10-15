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
    @SerialName("memberPostId") val id: Long,
    @SerialName("author") val author: MemberResponse,
    @SerialName("content") val content: String,
    @SerialName("images") val images: List<String>,
    @SerialName("createdAt") val createdAt: Instant,
    @SerialName("likeStatusResponse") val likeStatus: LikeStatusResponse,
    @SerialName("commentCount") val commentCount: Long,
)

@OptIn(ExperimentalTime::class)
internal fun MemberPostResponse.toMemberPost(): MemberPost = MemberPost(
    id = this.id,
    author = this.author.toModel(),
    content = this.content,
    images = this.images,
    createdAt = this.createdAt.toStdlibInstant(),
    isLiked = false,
    likeCount = 0,
    commentCount = commentCount,
)
