package com.parksupark.soomjae.features.auth.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class EmailLoginRequest(
    val email: String,
    val password: String,
)
