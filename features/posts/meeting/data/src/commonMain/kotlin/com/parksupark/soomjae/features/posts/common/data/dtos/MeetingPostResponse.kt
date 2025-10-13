package com.parksupark.soomjae.features.posts.common.data.dtos

import com.parksupark.soomjae.features.posts.common.data.category.dtos.CategoryResponse
import com.parksupark.soomjae.features.posts.common.data.category.dtos.toDomain
import com.parksupark.soomjae.features.posts.common.data.common.dtos.MemberResponse
import com.parksupark.soomjae.features.posts.common.data.common.dtos.toModel
import com.parksupark.soomjae.features.posts.common.data.location.dtos.LocationResponse
import com.parksupark.soomjae.features.posts.common.data.location.dtos.toLocation
import com.parksupark.soomjae.features.posts.common.domain.models.MeetingPost
import kotlin.time.ExperimentalTime
import kotlinx.datetime.Instant
import kotlinx.datetime.toStdlibInstant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@OptIn(ExperimentalTime::class)
@Serializable
internal data class MeetingPostResponse(
    @SerialName("postId") val id: Long,
    @SerialName("title") val title: String,
    @SerialName("content") val content: String,
    @SerialName("author") val author: MemberResponse,
    @SerialName("createdTime") val createdAt: Instant,
    @SerialName("category") val category: CategoryResponse?,
    @SerialName("location") val locationCode: LocationResponse?,
)

@OptIn(ExperimentalTime::class)
internal fun MeetingPostResponse.toMeetingPost(): MeetingPost = MeetingPost(
    id = id,
    title = title,
    content = content,
    author = author.toModel(),
    createdAt = createdAt.toStdlibInstant(),
    category = category?.toDomain(),
    location = locationCode?.toLocation(),
    isUserLiked = false, // TODO: implement this value
)
