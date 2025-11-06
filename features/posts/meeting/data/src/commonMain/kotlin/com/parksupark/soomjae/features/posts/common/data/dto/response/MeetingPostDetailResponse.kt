package com.parksupark.soomjae.features.posts.common.data.dto.response

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
import com.parksupark.soomjae.features.posts.common.domain.models.RecruitmentPeriod
import kotlin.time.ExperimentalTime
import kotlinx.datetime.Instant
import kotlinx.datetime.toStdlibInstant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class MeetingPostDetailResponse(
    @SerialName("postId") val postId: Long,
    @SerialName("title") val title: String,
    @SerialName("content") val content: String,
    @SerialName("author") val author: MemberResponse,
    @SerialName("createdTime") val createdAt: Instant,
    @SerialName("category") val category: CategoryResponse?,
    @SerialName("location") val location: LocationResponse?,

    @SerialName("likeNum") val likeCount: Long,
    @SerialName("isLikedByMe") val isUserLiked: Boolean,

    @SerialName("maximumParticipants") val maxParticipantCount: Int,
    @SerialName("currentParticipantCount") val currentParticipantCount: Int,
    @SerialName("isParticipatedByMe") val isUserJoined: Boolean,

    @SerialName("startTime") val recruitmentStartTime: Instant,
    @SerialName("endTime") val recruitmentEndTime: Instant,

    @SerialName("comments") val comments: List<CommentResponse>,
)

@OptIn(ExperimentalTime::class)
internal fun MeetingPostDetailResponse.toMeetingPostDetail() = MeetingPostDetail(
    post = MeetingPost(
        id = postId,
        title = title,
        content = content,
        author = author.toModel(),
        createdAt = createdAt.toStdlibInstant(),
        category = category?.toDomain(),
        location = location?.toLocation(),
        likeCount = likeCount.toInt(),
        isUserLiked = isUserLiked,
        commentCount = comments.count(),
        maxParticipationCount = maxParticipantCount,
        currentParticipantCount = currentParticipantCount,
        recruitmentPeriod = RecruitmentPeriod(
            startTime = recruitmentStartTime.toStdlibInstant(),
            endTime = recruitmentEndTime.toStdlibInstant(),
        ),
    ),
    maxParticipationCount = this.maxParticipantCount,
    currentParticipantCount = this.currentParticipantCount,
    isUserJoined = this.isUserJoined,
    recruitmentPeriod = RecruitmentPeriod(
        startTime = this.recruitmentStartTime.toStdlibInstant(),
        endTime = this.recruitmentEndTime.toStdlibInstant(),
    ),
    comments = comments.map { it.toComment() },
)
