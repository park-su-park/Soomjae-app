package com.parksupark.soomjae.features.profile.data.model.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CheckNicknameDuplicateResponse(
    @SerialName("duplicate") val isDuplicated: Boolean,
)
