package com.parksupark.soomjae.features.auth.data.repositories

import arrow.core.Either
import com.parksupark.soomjae.core.common.utils.mapToEmpty
import com.parksupark.soomjae.core.domain.auth.models.AuthInfo
import com.parksupark.soomjae.core.domain.auth.repositories.SessionRepository
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.features.auth.data.datasources.local.LocalAuthDataSource
import com.parksupark.soomjae.features.auth.data.datasources.remote.RemoteAuthDataSource
import com.parksupark.soomjae.features.auth.domain.repositories.AuthRepository

internal class AuthRepositoryImpl(
    private val remoteAuthDataSource: RemoteAuthDataSource,
    private val localAuthDataSource: LocalAuthDataSource,
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

    override suspend fun saveEmail(email: String): Either<DataFailure.Local, Unit> = localAuthDataSource.saveEmail(email)

    override suspend fun loadSavedEmail(): Either<DataFailure.Local, String> = localAuthDataSource.loadSavedEmail()

    override suspend fun clearSavedEmail(): Either<DataFailure.Local, Unit> = localAuthDataSource.clearSavedEmail()
}
