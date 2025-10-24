package com.parksupark.soomjae.features.posts.community.data.remote.dto

import com.parksupark.soomjae.features.posts.common.data.common.dtos.MemberResponse
import com.parksupark.soomjae.features.posts.common.data.common.dtos.toModel
import com.parksupark.soomjae.features.posts.community.domain.model.CommunityPost
import kotlin.time.ExperimentalTime
import kotlinx.datetime.Instant
import kotlinx.datetime.toStdlibInstant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// TODO: Remove deprecation warning when serialization library is updated to use kotlin.time.Instant
@Suppress("DEPRECATION")
@OptIn(ExperimentalTime::class)
@Serializable
internal data class CommunityPostResponse(
    @SerialName("postId") val postId: Long,
    @SerialName("title") val title: String,
    @SerialName("content") val content: String,
    @SerialName("author") val author: MemberResponse,
    @SerialName("createdTime") val createdAt: Instant,

    @SerialName("category") val categoryName: String? = null,
    @SerialName("location") val locationName: String? = null,

    @SerialName("likeNum") val likeCount: Int,
    @SerialName("isLikedByMe") val isUserLiked: Boolean,
)

@OptIn(ExperimentalTime::class)
internal fun CommunityPostResponse.toModel(): CommunityPost = CommunityPost(
    id = postId,
    title = title,
    content = content,
    author = author.toModel(),
    createdAt = createdAt.toStdlibInstant(),
    categoryName = categoryName,
    locationName = locationName,
    likeCount = likeCount,
    isUserLiked = isUserLiked,
)
