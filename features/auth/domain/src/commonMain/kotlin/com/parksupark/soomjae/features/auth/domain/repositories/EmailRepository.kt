package com.parksupark.soomjae.features.auth.domain.repositories

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.features.auth.domain.failures.VerificationFailure

interface EmailRepository {
    suspend fun sendVerificationCode(email: String): Either<DataFailure, Unit>

    suspend fun verifyCode(
        email: String,
        code: String,
    ): Either<VerificationFailure, Unit>
}
