package com.parksupark.soomjae.features.posts.common.data.dto.response

import kotlin.time.ExperimentalTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@OptIn(ExperimentalTime::class)
@Serializable
internal data class MeetingPostsResponse(
    @SerialName("posts") val posts: List<MeetingPostResponse>,
)
