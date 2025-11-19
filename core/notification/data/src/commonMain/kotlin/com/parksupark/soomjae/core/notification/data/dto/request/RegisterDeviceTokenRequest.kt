package com.parksupark.soomjae.core.notification.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterDeviceTokenRequest(
    @SerialName("fcmToken") val token: String,
    @SerialName("device") val deviceId: String,
//    @SerialName("platform") val platform: String,
)
