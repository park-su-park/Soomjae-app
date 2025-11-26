package com.parksupark.soomjae.features.auth.data.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class EmailVerificationRequest(
    val email: String,
)
