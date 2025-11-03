package com.parksupark.soomjae.core.notification.data.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.parksupark.soomjae.core.domain.auth.repositories.SessionRepository
import com.parksupark.soomjae.core.notification.domain.service.DeviceTokenService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class SoomjaeFirebaseMessagingService : FirebaseMessagingService() {
    private val deviceTokenService by inject<DeviceTokenService>()
    private val sessionRepository by inject<SessionRepository>()
    private val applicationScope by inject<CoroutineScope>()

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        applicationScope.launch {
            val authInfo = sessionRepository.getAsFlow().first()
            if (authInfo != null) {
                deviceTokenService.registerToken(
                    token = token,
                    platform = "ANDROID",
                )
            }
        }
    }
}
