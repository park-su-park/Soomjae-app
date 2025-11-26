package com.parksupark.soomjae.features.posts.common.data.network.dto

import com.parksupark.soomjae.core.domain.post.model.UpdateMeetingPost
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlinx.serialization.Serializable

@OptIn(ExperimentalTime::class)
@Serializable
data class PutMeetingPostRequest(
    val title: String,
    val content: String,
    val categoryId: Long?,
    val locationCode: Long?,
    val maxParticipants: Int,
    val startTime: Instant,
    val endTime: Instant,
)

@OptIn(ExperimentalTime::class)
fun UpdateMeetingPost.toPutMeetingPostRequest(): PutMeetingPostRequest = PutMeetingPostRequest(
    title = this.title,
    content = this.content,
    categoryId = this.categoryId,
    locationCode = this.locationCode,
    maxParticipants = this.maxParticipationCount ?: -1,
    startTime = this.recruitmentPeriod.startTime,
    endTime = this.recruitmentPeriod.endTime,
)
