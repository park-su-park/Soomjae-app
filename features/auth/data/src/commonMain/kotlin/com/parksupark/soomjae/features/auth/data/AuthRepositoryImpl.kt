package com.parksupark.soomjae.features.auth.data

import arrow.core.Either
import com.parksupark.soomjae.core.domain.auth.models.AuthInfo
import com.parksupark.soomjae.core.domain.auth.repositories.SessionRepository
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.remote.networking.post
import com.parksupark.soomjae.features.auth.data.dto.CheckEmailAvailableRequest
import com.parksupark.soomjae.features.auth.data.dto.CheckEmailAvailableResponse
import com.parksupark.soomjae.features.auth.data.dto.EmailLoginRequest
import com.parksupark.soomjae.features.auth.data.dto.EmailLoginResponse
import com.parksupark.soomjae.features.auth.data.dto.RegisterRequest
import com.parksupark.soomjae.features.auth.domain.AuthRepository
import io.ktor.client.HttpClient

class AuthRepositoryImpl(
    private val httpClient: HttpClient,
    private val sessionRepository: SessionRepository,
) : AuthRepository {
    override suspend fun register(
        email: String,
        password: String,
    ): Either<DataFailure.Network, Unit> = httpClient.post<RegisterRequest, Unit>(
        route = "/v1/create-member",
        body = RegisterRequest(
            email = email,
            password = password,
        ),
    )

    override suspend fun login(
        email: String,
        password: String,
    ): Either<DataFailure.Network, Unit> = httpClient.post<EmailLoginRequest, EmailLoginResponse>(
        route = "/auth/login",
        body = EmailLoginRequest(
            email = email,
            password = password,
        ),
    ).map {
        sessionRepository.set(
            AuthInfo(accessToken = it.accessToken),
        )
    }

    override suspend fun checkEmailAvailable(email: String): Either<DataFailure.Network, Boolean> =
        httpClient.post<CheckEmailAvailableRequest, CheckEmailAvailableResponse>(
            route = "/v1/members/check-duplicate-email",
            body = CheckEmailAvailableRequest(
                email = email,
            ),
        ).map {
            !it.duplicate
        }
}
