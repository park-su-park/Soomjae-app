package com.parksupark.soomjae.core.domain.auth.models

data class AuthInfo(
    val accessToken: String,
    val refreshToken: String,
    val userId: String,
)
