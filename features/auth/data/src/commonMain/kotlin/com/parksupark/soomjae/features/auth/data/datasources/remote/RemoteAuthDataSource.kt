package com.parksupark.soomjae.features.auth.data.datasources.remote

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.remote.networking.post
import com.parksupark.soomjae.features.auth.data.dto.CheckEmailAvailableRequest
import com.parksupark.soomjae.features.auth.data.dto.CheckEmailAvailableResponse
import com.parksupark.soomjae.features.auth.data.dto.EmailLoginRequest
import com.parksupark.soomjae.features.auth.data.dto.EmailLoginResponse
import com.parksupark.soomjae.features.auth.data.dto.RegisterRequest
import io.ktor.client.HttpClient

internal class RemoteAuthDataSource(
    private val httpClient: HttpClient,
) {
    suspend fun register(
        email: String,
        password: String,
        nickname: String,
    ): Either<DataFailure.Network, Unit> = httpClient.post<RegisterRequest, Unit>(
        route = "/v1/members/create-member",
        body = RegisterRequest(
            email = email,
            password = password,
            nickname = nickname,
        ),
    )

    suspend fun login(
        email: String,
        password: String,
    ): Either<DataFailure.Network, EmailLoginResponse> = httpClient.post<EmailLoginRequest, EmailLoginResponse>(
        route = "/auth/login",
        body = EmailLoginRequest(
            email = email,
            password = password,
        ),
    )

    suspend fun checkEmailAvailable(email: String): Either<DataFailure.Network, Boolean> =
        httpClient.post<CheckEmailAvailableRequest, CheckEmailAvailableResponse>(
            route = "/v1/members/check-duplicate-email",
            body = CheckEmailAvailableRequest(
                email = email,
            ),
        ).map {
            !it.duplicate
        }
}
