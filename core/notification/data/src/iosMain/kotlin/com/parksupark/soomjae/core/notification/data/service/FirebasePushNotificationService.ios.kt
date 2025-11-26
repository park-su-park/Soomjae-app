package com.parksupark.soomjae.core.notification.data.service

import com.parksupark.soomjae.core.notification.data.IosDeviceTokenHolder
import com.parksupark.soomjae.core.notification.domain.service.PushNotificationService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart
import platform.UIKit.registerForRemoteNotifications

actual class FirebasePushNotificationService : PushNotificationService {
    actual override fun observeDeviceToken(): Flow<String?> = IosDeviceTokenHolder
        .token
        .onStart {
            if (IosDeviceTokenHolder.token.value == null) {
                val userDefaults = platform.Foundation.NSUserDefaults.standardUserDefaults
                val fcmToken = userDefaults.stringForKey("FCM_TOKEN")

                if (fcmToken != null) {
                    IosDeviceTokenHolder.updateToken(fcmToken)
                } else {
                    platform.UIKit.UIApplication.sharedApplication.registerForRemoteNotifications()
                }
            }
        }

    actual override fun observeDeviceId(): Flow<String?> = IosDeviceTokenHolder
        .deviceId
        .onStart {
            if (IosDeviceTokenHolder.deviceId.value == null) {
                val userDefaults = platform.Foundation.NSUserDefaults.standardUserDefaults
                val deviceId = userDefaults.stringForKey("DEVICE_ID")

                if (deviceId != null) {
                    IosDeviceTokenHolder.updateDeviceId(deviceId)
                }
            }
        }
}
