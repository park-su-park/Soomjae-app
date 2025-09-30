package com.parksupark.soomjae.features.posts.common.data.dtos

import com.parksupark.soomjae.features.posts.common.data.common.dtos.MemberResponse
import kotlinx.serialization.Serializable

@Serializable
internal data class ParticipantListResponse(
    val participants: List<MemberResponse>,
)
