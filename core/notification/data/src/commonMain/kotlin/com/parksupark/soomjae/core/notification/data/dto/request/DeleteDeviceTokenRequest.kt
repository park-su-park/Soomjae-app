package com.parksupark.soomjae.core.notification.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class DeleteDeviceTokenRequest(
    @SerialName("device") val deviceId: String,
)
