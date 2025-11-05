package com.parksupark.soomjae.core.notification.data.service

import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging
import com.parksupark.soomjae.core.domain.logging.SjLogger
import com.parksupark.soomjae.core.notification.domain.service.PushNotificationService
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

private const val TAG = "FirebasePushNotificationService"

actual class FirebasePushNotificationService(
    private val logger: SjLogger,
) : PushNotificationService {

    actual override fun observeDeviceToken(): Flow<String?> = flow {
        try {
            val fcmToken = Firebase.messaging.token.await()
            logger.info(TAG, "Initial FCM token received: $fcmToken")
            emit(fcmToken)
        } catch (e: Exception) {
            currentCoroutineContext().ensureActive()
            logger.error(TAG, "Failed to get FCM token", e)
            emit(null)
        }
    }
}
