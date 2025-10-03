package com.parksupark.soomjae.features.posts.common.data.dtos

import kotlinx.serialization.Serializable

@Serializable
internal data class ParticipationResponse(
    val postId: Long,
    val joined: Boolean,
    val participantCount: Int,
)
