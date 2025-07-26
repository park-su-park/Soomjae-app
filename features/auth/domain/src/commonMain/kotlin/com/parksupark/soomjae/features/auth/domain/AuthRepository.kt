package com.parksupark.soomjae.features.auth.domain

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure

interface AuthRepository {
    suspend fun register(
        email: String,
        password: String,
        nickname: String,
    ): Either<DataFailure.Network, Unit>

    suspend fun login(
        email: String,
        password: String,
    ): Either<DataFailure.Network, Unit>

    suspend fun checkEmailAvailable(email: String): Either<DataFailure.Network, Boolean>
}
