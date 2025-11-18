package com.parksupark.soomjae.features.profile.data.model.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileResponse(
    @SerialName("memberId") val memberId: Long,
    @SerialName("nickname") val nickname: String,
    @SerialName("bio") private val _bio: String?,
    @SerialName("profileImageUrl") val profileImageUrl: String,
) {
    val bio: String get() = _bio ?: ""
}
