package com.parksupark.soomjae.features.profile.data.model.dto.response

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class AuthorResponse(
    @SerialName("memberId") val memberId: Long,
    @SerialName("nickname") val nickname: String,
    @SerialName("profileImageUrl") val profileImageUrl: String,
    @SerialName("email") val email: String,
    @SerialName("createdTime") val createdTime: Instant,
    @SerialName("modifiedTime") val modifiedTime: Instant,
)
