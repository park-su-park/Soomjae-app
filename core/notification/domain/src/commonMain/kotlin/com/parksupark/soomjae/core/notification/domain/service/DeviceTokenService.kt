package com.parksupark.soomjae.core.notification.domain.service

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure

interface DeviceTokenService {

    suspend fun registerToken(
        token: String,
        deviceId: String,
        platform: String,
    ): Either<DataFailure.Network, Unit>

    suspend fun unregisterToken(deviceId: String): Either<DataFailure.Network, Unit>
}
