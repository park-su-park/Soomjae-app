package com.parksupark.soomjae.features.posts.member.data.model.dto.response

import com.parksupark.soomjae.features.posts.common.data.common.dtos.CommentResponse
import com.parksupark.soomjae.features.posts.common.data.common.dtos.MemberResponse
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class FetchMemberPostDetailResponse(
    @SerialName("memberPostId") val id: Long,
    @SerialName("author") val member: MemberResponse,
    @SerialName("images") val images: List<String>,
    @SerialName("content") val content: String,
    @SerialName("likeStatusResponse") val likeStatusResponse: LikeStatusResponse,
    @SerialName("comments") val comments: List<CommentResponse>,
    @SerialName("commentCount") val commentCount: Int,
    @SerialName("createdAt") val createdAt: Instant,
)
