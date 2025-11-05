package com.parksupark.soomjae.features.posts.meeting.presentation.tab.models

import androidx.compose.runtime.Composable
import com.parksupark.soomjae.core.presentation.ui.utils.toRelativeTimeString
import com.parksupark.soomjae.features.posts.common.domain.models.MeetingPost
import com.parksupark.soomjae.features.posts.common.presentation.models.AuthorUi
import com.parksupark.soomjae.features.posts.common.presentation.models.CategoryUi
import com.parksupark.soomjae.features.posts.common.presentation.models.toUi
import com.parksupark.soomjae.features.posts.meeting.presentation.detail.models.RecruitmentPeriodUi
import com.parksupark.soomjae.features.posts.meeting.presentation.detail.models.toRecruitmentPeriodUi
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
data class MeetingPostUi(
    val id: Long,
    val title: String,
    val content: String,
    val createdAt: Instant,
    val author: AuthorUi,
    val isUserLiked: Boolean,
    val likeCount: Int,
    val commentCount: Int,
    val category: CategoryUi?,

    val maxParticipantCount: Int,
    val currentParticipantCount: Int,

    val recruitmentPeriod: RecruitmentPeriodUi,
) {
    val formattedCreatedAt: String
        @Composable get() = createdAt.toRelativeTimeString()

    val isExpired: Boolean
        get() = recruitmentPeriod.endTime.isBefore(Clock.System.now())

    val isFull: Boolean
        get() = currentParticipantCount >= maxParticipantCount
}

@OptIn(ExperimentalTime::class)
private fun Instant.isBefore(now: Instant) = this < now

@ExperimentalTime
internal fun MeetingPost.toMeetingPostUi() = MeetingPostUi(
    id = id,
    title = title,
    content = content,
    createdAt = createdAt,
    author = author.toUi(),
    isUserLiked = isUserLiked,
    likeCount = likeCount,
    commentCount = commentCount,
    category = category?.toUi(),
    maxParticipantCount = maxParticipationCount,
    currentParticipantCount = currentParticipantCount,
    recruitmentPeriod = recruitmentPeriod.toRecruitmentPeriodUi(),
)
