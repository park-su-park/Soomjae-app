package com.parksupark.soomjae.features.auth.data.datasources.remote

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.remote.networking.post
import com.parksupark.soomjae.core.remote.networking.put
import com.parksupark.soomjae.features.auth.data.dto.request.EmailCodeVerificationRequest
import com.parksupark.soomjae.features.auth.data.dto.request.EmailVerificationRequest
import com.parksupark.soomjae.features.auth.domain.failures.VerificationFailure
import io.ktor.client.HttpClient

internal class KtorEmailDataSource(
    private val httpClient: HttpClient,
) {
    suspend fun requestEmailVerification(email: String): Either<DataFailure.Network, Unit> =
        httpClient.post<EmailVerificationRequest, Unit>(
            route = "/v1/email/verification",
            body = EmailVerificationRequest(email),
        )

    suspend fun verifyCode(
        email: String,
        code: String,
    ): Either<VerificationFailure, Unit> = httpClient.put<EmailCodeVerificationRequest, Unit>(
        route = "/v1/email/verification",
        body = EmailCodeVerificationRequest(email, code),
    ).mapLeft { failure ->
        if (failure == DataFailure.Network.BAD_REQUEST) {
            VerificationFailure.InvalidCode
        } else {
            VerificationFailure.DataFailure(failure)
        }
    }
}
