package com.parksupark.soomjae.core.data.auth.entities

import com.parksupark.soomjae.core.domain.auth.models.AuthInfo
import kotlinx.serialization.Serializable

@Serializable
data class AuthInfoSerializable(
    val accessToken: String,
    val refreshToken: String,
    val userId: String,
)

fun AuthInfo.toAuthInfoSerializable(): AuthInfoSerializable = AuthInfoSerializable(
    accessToken = accessToken,
    refreshToken = refreshToken,
    userId = userId,
)

fun AuthInfoSerializable.toAuthInfo(): AuthInfo = AuthInfo(
    accessToken = accessToken,
    refreshToken = refreshToken,
    userId = userId,
)
