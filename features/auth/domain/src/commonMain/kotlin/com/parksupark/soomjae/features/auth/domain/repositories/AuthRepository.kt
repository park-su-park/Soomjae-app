package com.parksupark.soomjae.features.auth.domain.repositories

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure

interface AuthRepository {
    suspend fun register(
        email: String,
        password: String,
    ): Either<DataFailure.Network, Unit>

    suspend fun login(
        email: String,
        password: String,
    ): Either<DataFailure.Network, Unit>

    suspend fun checkEmailAvailable(email: String): Either<DataFailure, Boolean>

    suspend fun saveEmail(email: String): Either<DataFailure.Local, Unit>

    suspend fun loadSavedEmail(): Either<DataFailure.Local, String>

    suspend fun clearSavedEmail(): Either<DataFailure.Local, Unit>
}
