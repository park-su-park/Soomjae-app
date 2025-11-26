package com.parksupark.soomjae.features.auth.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val email: String,
    val password: String,
)
