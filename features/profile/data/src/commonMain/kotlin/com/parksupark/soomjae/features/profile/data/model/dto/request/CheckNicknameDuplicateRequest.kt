package com.parksupark.soomjae.features.profile.data.model.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CheckNicknameDuplicateRequest(
    @SerialName("nickname") val nickname: String,
)
