package com.parksupark.soomjae.features.posts.common.data.dto.request

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PostMeetingPostRequest(
    @SerialName("title") val title: String,
    @SerialName("content") val content: String,
    @SerialName("category") val categoryId: Long?,
    @SerialName("location") val locationCode: Long?,
    @SerialName("startTime") val startAt: Instant,
    @SerialName("endTime") val endAt: Instant,
    @SerialName("maximumParticipants") val maxParticipants: Long,
)
