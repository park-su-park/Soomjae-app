package com.parksupark.soomjae.features.posts.community.data.dtos

import com.parksupark.soomjae.features.posts.common.data.dtos.MemberResponse
import com.parksupark.soomjae.features.posts.common.data.dtos.toModel
import com.parksupark.soomjae.features.posts.community.domain.models.CommunityPost
import com.parksupark.soomjae.features.posts.community.domain.models.CommunityPostDetail
import kotlin.time.ExperimentalTime
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class CommunityPostResponse(
    @SerialName("postId") val id: Long,
    @SerialName("title") val title: String,
    @SerialName("content") val content: String,
    @SerialName("author") val author: MemberResponse,
    @SerialName("createdTime") val createdAt: LocalDateTime,
)

@OptIn(ExperimentalTime::class)
internal fun CommunityPostResponse.toModel(): CommunityPost = CommunityPost(
    id = id,
    title = title,
    content = content,
    author = author.toModel(),
    createdAt = createdAt.toInstant(TimeZone.UTC),
)

@OptIn(ExperimentalTime::class)
internal fun CommunityPostResponse.toCommunityPostDetail(): CommunityPostDetail = CommunityPostDetail(
    id = id,
    title = title,
    content = content,
    author = author.toModel(),
    createdAt = createdAt.toInstant(TimeZone.UTC),
    // TODO: Implement Logic to get comments
    comments = emptyList(),
)
