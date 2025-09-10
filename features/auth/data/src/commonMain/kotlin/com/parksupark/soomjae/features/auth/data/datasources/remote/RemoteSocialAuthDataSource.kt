package com.parksupark.soomjae.features.auth.data.datasources.remote

import arrow.core.Either
import com.parksupark.soomjae.core.domain.auth.models.AuthInfo
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.remote.networking.post
import io.ktor.client.HttpClient

internal class RemoteSocialAuthDataSource(
    private val httpClient: HttpClient,
) {
    suspend fun signInWithGoogle(idToken: String): Either<DataFailure.Network, AuthInfo?> {
        // TODO: ensure to replace the empty route with actual API endpoint
        return httpClient.post<Map<String, String>, AuthInfo>(
            route = "/api/v1/auth/google",
            body = mapOf("idToken" to idToken),
        ).map {
            if (it.accessToken.isNotEmpty()) {
                it
            } else {
                null
            }
        }
    }
}
