package com.parksupark.soomjae.features.auth.data.repositories

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.features.auth.data.datasources.remote.KtorEmailDataSource
import com.parksupark.soomjae.features.auth.domain.failures.VerificationFailure
import com.parksupark.soomjae.features.auth.domain.repositories.EmailRepository

internal class DefaultEmailRepository(
    private val remoteDataSource: KtorEmailDataSource,
) : EmailRepository {
    override suspend fun sendVerificationCode(email: String): Either<DataFailure, Unit> = remoteDataSource.requestEmailVerification(email)

    override suspend fun verifyCode(
        email: String,
        code: String,
    ): Either<VerificationFailure, Unit> = remoteDataSource.verifyCode(email, code)
}
