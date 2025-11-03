package com.parksupark.soomjae.core.notification.data.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class RegisterDeviceTokenRequest(
    val token: String,
    val platform: String,
)
