package com.parksupark.soomjae.features.profile.data.model.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FetchProfileResponse(
    @SerialName("memberId") val memberId: Long,
    @SerialName("nickname") val nickname: String,
    @SerialName("bio") val bio: String,
    @SerialName("profileImageUrl") val profileImageUrl: String,
)
