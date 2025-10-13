package com.parksupark.soomjae.core.data.auth.entities

import com.parksupark.soomjae.core.domain.auth.models.AuthInfo
import kotlinx.serialization.Serializable

@Serializable
data class AuthInfoSerializable(
    val memberId: Long,
    val accessToken: String,
)

internal fun AuthInfo.toAuthInfoSerializable(): AuthInfoSerializable = AuthInfoSerializable(
    memberId = memberId,
    accessToken = accessToken,
)

internal fun AuthInfoSerializable.toAuthInfo(): AuthInfo = AuthInfo(
    memberId = memberId,
    accessToken = accessToken,
)
