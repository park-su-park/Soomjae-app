package com.parksupark.soomjae.core.notification.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object IosDeviceTokenHolder {
    private val _token = MutableStateFlow<String?>(null)
    val token = _token.asStateFlow()

    private val _deviceId = MutableStateFlow<String?>(null)
    val deviceId = _deviceId.asStateFlow()

    fun updateToken(token: String?) {
        _token.value = token
    }

    fun updateDeviceId(deviceId: String?) {
        _deviceId.value = deviceId
    }
}
