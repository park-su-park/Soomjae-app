package com.parksupark.soomjae.features.posts.common.data.dtos

import com.parksupark.soomjae.features.posts.common.domain.models.UpdatedParticipation
import kotlinx.serialization.Serializable

@Serializable
internal data class ParticipationResponse(
    val postId: Long,
    val joined: Boolean,
    val participantCount: Int,
)

internal fun ParticipationResponse.toUpdatedParticipation() = UpdatedParticipation(
    meetingId = postId,
    joined = joined,
    participantCount = participantCount,
)
