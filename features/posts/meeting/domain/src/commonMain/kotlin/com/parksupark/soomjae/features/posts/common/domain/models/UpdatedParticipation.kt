package com.parksupark.soomjae.features.posts.common.domain.models

data class UpdatedParticipation(
    val meetingId: Long,
    val joined: Boolean,
    val participantCount: Int,
)
