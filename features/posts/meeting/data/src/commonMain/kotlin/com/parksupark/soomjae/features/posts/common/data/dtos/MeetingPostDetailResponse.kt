package com.parksupark.soomjae.features.posts.common.data.dtos

import com.parksupark.soomjae.features.posts.common.data.category.dtos.CategoryResponse
import com.parksupark.soomjae.features.posts.common.data.category.dtos.toDomain
import com.parksupark.soomjae.features.posts.common.data.common.dtos.CommentResponse
import com.parksupark.soomjae.features.posts.common.data.common.dtos.MemberResponse
import com.parksupark.soomjae.features.posts.common.data.common.dtos.toComment
import com.parksupark.soomjae.features.posts.common.data.common.dtos.toModel
import com.parksupark.soomjae.features.posts.common.data.location.dtos.LocationResponse
import com.parksupark.soomjae.features.posts.common.data.location.dtos.toLocation
import com.parksupark.soomjae.features.posts.common.domain.models.MeetingPost
import com.parksupark.soomjae.features.posts.common.domain.models.MeetingPostDetail
import kotlin.time.ExperimentalTime
import kotlinx.datetime.Instant
import kotlinx.datetime.toStdlibInstant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class MeetingPostDetailResponse(
    @SerialName("postId") val id: Long,
    @SerialName("title") val title: String,
    @SerialName("content") val content: String,
    @SerialName("author") val author: MemberResponse,
    @SerialName("createdTime") val createdAt: Instant,
    @SerialName("category") val category: CategoryResponse?,
    @SerialName("location") val location: LocationResponse?,

    @SerialName("comments") val comments: List<CommentResponse>,
)

@OptIn(ExperimentalTime::class)
internal fun MeetingPostDetailResponse.toMeetingPostDetail() = MeetingPostDetail(
    post = MeetingPost(
        id = id,
        title = title,
        content = content,
        author = author.toModel(),
        createdAt = createdAt.toStdlibInstant(),
        category = category?.toDomain(),
        location = location?.toLocation(),
    ),
    comments = comments.map { it.toComment() },
)
