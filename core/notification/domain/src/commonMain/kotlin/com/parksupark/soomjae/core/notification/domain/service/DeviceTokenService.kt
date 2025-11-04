package com.parksupark.soomjae.core.notification.domain.service

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure

interface DeviceTokenService {

    suspend fun registerToken(
        token: String,
        platform: String,
    ): Either<DataFailure.Network, Unit>

    suspend fun unregisterToken(token: String): Either<DataFailure.Network, Unit>
}
