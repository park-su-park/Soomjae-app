package com.parksupark.soomjae.features.auth.data.datasources.remote

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.remote.networking.post
import com.parksupark.soomjae.features.auth.data.dto.SocialLoginResponse
import io.ktor.client.HttpClient

internal class RemoteSocialAuthDataSource(
    private val httpClient: HttpClient,
) {
    suspend fun signInWithGoogle(
        idToken: String,
    ): Either<DataFailure.Network, SocialLoginResponse?> =
        httpClient.post<Map<String, String>, SocialLoginResponse>(
            route = "/v1/oauth2/google/id-token",
            body = mapOf("idToken" to idToken),
        ).map {
            if (it.accessToken.isNotEmpty()) {
                it
            } else {
                null
            }
        }
}
