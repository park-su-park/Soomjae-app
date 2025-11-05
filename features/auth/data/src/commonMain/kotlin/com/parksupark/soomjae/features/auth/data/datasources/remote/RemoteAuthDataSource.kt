package com.parksupark.soomjae.features.auth.data.datasources.remote

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.remote.networking.constructRoute
import com.parksupark.soomjae.core.remote.networking.post
import com.parksupark.soomjae.core.remote.networking.safeCall
import com.parksupark.soomjae.core.remote.util.getRefreshTokenFromCookies
import com.parksupark.soomjae.features.auth.data.dto.CheckEmailAvailableRequest
import com.parksupark.soomjae.features.auth.data.dto.CheckEmailAvailableResponse
import com.parksupark.soomjae.features.auth.data.dto.EmailLoginRequest
import com.parksupark.soomjae.features.auth.data.dto.EmailLoginResponse
import com.parksupark.soomjae.features.auth.data.dto.RegisterRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url

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
    ): Either<DataFailure.Network, EmailLoginResponse> {
        var refreshToken: String? = null
        val response = safeCall<EmailLoginResponse> {
            val response = httpClient.post {
                this.url(constructRoute(route = "/v1/auth/login"))
                setBody(
                    body = EmailLoginRequest(
                        email = email,
                        password = password,
                    ),
                )
            }

            refreshToken = response.getRefreshTokenFromCookies()

            response
        }
        return response.map { response ->
            response.copy(
                refreshToken = refreshToken,
            )
        }
    }

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
