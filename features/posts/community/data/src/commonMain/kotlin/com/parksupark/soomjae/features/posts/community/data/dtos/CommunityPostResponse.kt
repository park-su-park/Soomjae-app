package com.parksupark.soomjae.features.posts.community.data.dtos

import com.parksupark.soomjae.features.posts.common.data.dtos.MemberResponse
import com.parksupark.soomjae.features.posts.common.data.dtos.toModel
import com.parksupark.soomjae.features.posts.community.domain.models.CommunityPost
import com.parksupark.soomjae.features.posts.community.domain.models.CommunityPostDetail
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
    @SerialName("postId") val id: Long,
    @SerialName("title") val title: String,
    @SerialName("content") val content: String,
    @SerialName("author") val author: MemberResponse,
    @SerialName("createdTime") val createdAt: Instant,
)

@OptIn(ExperimentalTime::class)
internal fun CommunityPostResponse.toModel(): CommunityPost = CommunityPost(
    id = id,
    title = title,
    content = content,
    author = author.toModel(),
    createdAt = createdAt.toStdlibInstant(),
)

@OptIn(ExperimentalTime::class)
internal fun CommunityPostResponse.toCommunityPostDetail(): CommunityPostDetail = CommunityPostDetail(
    id = id,
    title = title,
    content = content,
    author = author.toModel(),
    createdAt = createdAt.toStdlibInstant(),
    // TODO: Implement Logic to get comments
    comments = emptyList(),
)
