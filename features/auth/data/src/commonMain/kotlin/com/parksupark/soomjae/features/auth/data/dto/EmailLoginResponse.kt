package com.parksupark.soomjae.features.auth.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class EmailLoginResponse(
    val accessToken: String,
)
