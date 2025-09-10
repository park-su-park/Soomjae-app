package com.parksupark.soomjae.features.auth.data.repositories

import arrow.core.Either
import com.parksupark.soomjae.core.domain.auth.repositories.SessionRepository
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.features.auth.data.datasources.remote.RemoteSocialAuthDataSource
import com.parksupark.soomjae.features.auth.domain.SocialAuthRepository

internal class SocialAuthRepositoryImpl(
    private val remoteDataSource: RemoteSocialAuthDataSource,
    private val sessionRepository: SessionRepository,
) : SocialAuthRepository {
    override suspend fun signInWithGoogle(idToken: String): Either<DataFailure.Network, Unit> =
        remoteDataSource.signInWithGoogle(idToken).map {
            sessionRepository.set(it)
        }
}
