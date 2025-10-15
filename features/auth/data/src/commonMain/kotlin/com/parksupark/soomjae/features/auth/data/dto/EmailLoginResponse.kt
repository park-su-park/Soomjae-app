package com.parksupark.soomjae.features.auth.data.dto

import com.parksupark.soomjae.core.domain.auth.models.AuthInfo
import kotlinx.serialization.Serializable

@Serializable
internal data class EmailLoginResponse(
    val memberId: Long,
    val accessToken: String,
)

internal fun EmailLoginResponse.toAuthInfo() = AuthInfo(
    memberId = memberId,
    accessToken = accessToken,
)
