package com.parksupark.soomjae.features.posts.common.data.dto.response

import com.parksupark.soomjae.features.posts.common.data.category.dtos.CategoryResponse
import com.parksupark.soomjae.features.posts.common.data.category.dtos.toDomain
import com.parksupark.soomjae.features.posts.common.data.common.dtos.MemberResponse
import com.parksupark.soomjae.features.posts.common.data.common.dtos.toModel
import com.parksupark.soomjae.features.posts.common.data.location.dtos.LocationResponse
import com.parksupark.soomjae.features.posts.common.data.location.dtos.toLocation
import com.parksupark.soomjae.features.posts.common.domain.models.MeetingPost
import com.parksupark.soomjae.features.posts.common.domain.models.RecruitmentPeriod
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
    @SerialName("likeNum") val likeCount: Long,
    @SerialName("isLikedByMe") val isUserLiked: Boolean,

    @SerialName("maximumParticipants") val maxParticipantCount: Int,
    @SerialName("currentParticipantCount") val currentParticipantCount: Int,

    @SerialName("startTime") val recruitmentStartTime: Instant,
    @SerialName("endTime") val recruitmentEndTime: Instant,
)

@OptIn(ExperimentalTime::class)
internal fun MeetingPostResponse.toMeetingPost(): MeetingPost = MeetingPost(
    id = id,
    title = title,
    content = content,
    createdAt = createdAt.toStdlibInstant(),
    author = author.toModel(),
    category = category?.toDomain(),
    location = locationCode?.toLocation(),
    likeCount = likeCount.toInt(),
    isUserLiked = isUserLiked,

    maxParticipationCount = maxParticipantCount,
    currentParticipantCount = currentParticipantCount,

    recruitmentPeriod = RecruitmentPeriod(
        recruitmentStartTime.toStdlibInstant(),
        recruitmentEndTime.toStdlibInstant(),
    ),
)
