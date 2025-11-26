package com.parksupark.soomjae.core.notification.data.service

import com.parksupark.soomjae.core.notification.domain.service.PushNotificationService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

actual class FirebasePushNotificationService : PushNotificationService {
    actual override fun observeDeviceToken(): Flow<String?> = emptyFlow()
    actual override fun observeDeviceId(): Flow<String?> = emptyFlow()
}
