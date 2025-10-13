package com.parksupark.soomjae.core.remote.dtos.response

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenResponse(
    val accessToken: String,
)
