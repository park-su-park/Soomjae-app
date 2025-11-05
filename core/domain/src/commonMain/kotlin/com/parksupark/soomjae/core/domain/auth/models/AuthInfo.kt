package com.parksupark.soomjae.core.domain.auth.models

data class AuthInfo(
    val memberId: Long,
    val accessToken: String,
    val refreshToken: String?,
)
