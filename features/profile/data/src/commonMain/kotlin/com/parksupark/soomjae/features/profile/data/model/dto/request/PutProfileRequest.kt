package com.parksupark.soomjae.features.profile.data.model.dto.request


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PutProfileRequest(
    @SerialName("nickname") val nickname: String? = null,
    @SerialName("bio") val bio: String,
    @SerialName("profileImageUrl") val profileImageUrl: String?,
)
