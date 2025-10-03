package com.parksupark.soomjae.features.posts.meeting.presentation.participant_list.model

import com.parksupark.soomjae.features.posts.common.domain.models.Participant

internal data class ParticipantUi(
    val id: String,
    val nickname: String,
    val profileImageUrl: String?,
    val isCurrentUser: Boolean,
)

internal fun Participant.toParticipantUi(currentUserId: String) = ParticipantUi(
    id = member.id,
    nickname = member.nickname,
    profileImageUrl = member.profileImageUrl,
    isCurrentUser = member.id == currentUserId,
)
