package com.parksupark.soomjae.core.notification.data.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.parksupark.soomjae.core.domain.auth.repositories.SessionRepository
import com.parksupark.soomjae.core.domain.logging.SjLogger
import com.parksupark.soomjae.core.notification.domain.service.DeviceTokenService
import com.parksupark.soomjae.core.notification.domain.service.PushNotificationService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

private const val TAG = "SoomjaeFirebaseMessagingService"

class SoomjaeFirebaseMessagingService : FirebaseMessagingService() {
    private val deviceTokenService by inject<DeviceTokenService>()
    private val sessionRepository by inject<SessionRepository>()
    private val pushNotificationService by inject<PushNotificationService>()
    private val applicationScope by inject<CoroutineScope>()
    private val logger by inject<SjLogger>()

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        applicationScope.launch {
            val authInfo = sessionRepository.getAsFlow().first()
            val deviceId = pushNotificationService.observeDeviceId().first()
            if (authInfo != null && deviceId != null) {
                logger.info(TAG, "onNewToken: Registering new device token.")
                deviceTokenService.registerToken(
                    token = token,
                    deviceId = deviceId,
                    platform = "ANDROID",
                )
            } else {
                logger.error(
                    TAG,
                    "onNewToken: User not logged in or deviceId is null, skipping token registration. deviceId=$deviceId",
                )
            }
        }
    }
}
