package com.parksupark.soomjae.features.auth.data.dto

import com.parksupark.soomjae.core.domain.auth.models.AuthInfo
import kotlinx.serialization.Serializable

@Serializable
internal data class SocialLoginResponse(
    val memberId: Long,
    val accessToken: String,
    val refreshToken: String? = null,
)

internal fun SocialLoginResponse?.toAuthInfo(): AuthInfo? {
    if (this == null) return null
    return AuthInfo(
        memberId = memberId,
        accessToken = this.accessToken,
        refreshToken = this.refreshToken,
    )
}
