package com.parksupark.soomjae

import com.parksupark.soomjae.core.notification.data.IosDeviceTokenHolder

object IosDeviceTokenHolderBridge {
    fun updateToken(token: String) {
        IosDeviceTokenHolder.updateToken(token)
    }
}
