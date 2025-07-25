package com.parksupark.soomjae.features.auth.data

import arrow.core.Either
import com.parksupark.soomjae.core.common.utils.mapToEmpty
import com.parksupark.soomjae.core.domain.auth.models.AuthInfo
import com.parksupark.soomjae.core.domain.auth.repositories.SessionRepository
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.features.auth.data.datasources.remote.RemoteAuthDataSource
import com.parksupark.soomjae.features.auth.domain.AuthRepository

class AuthRepositoryImpl(
    private val remoteAuthDataSource: RemoteAuthDataSource,
    private val sessionRepository: SessionRepository,
) : AuthRepository {
    override suspend fun register(
        email: String,
        password: String,
        nickname: String,
    ): Either<DataFailure.Network, Unit> = remoteAuthDataSource.register(
        email = email,
        password = password,
        nickname = nickname,
    )

    override suspend fun login(
        email: String,
        password: String,
    ): Either<DataFailure.Network, Unit> = remoteAuthDataSource.login(
        email = email,
        password = password,
    ).also { result ->
        result.onRight { response ->
            sessionRepository.set(AuthInfo(accessToken = response.accessToken))
        }
    }.mapToEmpty()

    override suspend fun checkEmailAvailable(email: String): Either<DataFailure.Network, Boolean> =
        remoteAuthDataSource.checkEmailAvailable(email)
}
