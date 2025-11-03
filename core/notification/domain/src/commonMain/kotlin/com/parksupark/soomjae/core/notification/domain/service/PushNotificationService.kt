package com.parksupark.soomjae.core.notification.domain.service

import kotlinx.coroutines.flow.Flow

interface PushNotificationService {
    fun observeDeviceToken(): Flow<String?>
}
