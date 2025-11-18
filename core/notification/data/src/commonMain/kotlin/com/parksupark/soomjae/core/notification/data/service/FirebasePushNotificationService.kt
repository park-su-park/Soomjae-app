package com.parksupark.soomjae.core.notification.data.service

import com.parksupark.soomjae.core.notification.domain.service.PushNotificationService
import kotlinx.coroutines.flow.Flow

expect class FirebasePushNotificationService : PushNotificationService {
    override fun observeDeviceToken(): Flow<String?>
    override fun observeDeviceId(): Flow<String?>
}
