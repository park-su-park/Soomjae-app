package com.parksupark.soomjae.features.auth.data

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.remote.networking.post
import com.parksupark.soomjae.features.auth.data.dto.RegisterRequest
import com.parksupark.soomjae.features.auth.domain.AuthRepository
import io.ktor.client.HttpClient

class AuthRepositoryImpl(
    private val httpClient: HttpClient,
) : AuthRepository {
    override suspend fun register(
        email: String,
        password: String,
    ): Either<DataFailure.Network, Unit> = httpClient.post<RegisterRequest, Unit>(
        route = "auth/register",
        body = RegisterRequest(
            email = email,
            password = password,
        ),
    )
}
