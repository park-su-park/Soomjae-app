package com.parksupark.soomjae.features.auth.data.datasources.remote

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.remote.networking.constructRoute
import com.parksupark.soomjae.core.remote.networking.safeCall
import com.parksupark.soomjae.core.remote.util.getRefreshTokenFromCookies
import com.parksupark.soomjae.features.auth.data.dto.SocialLoginResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url

internal class RemoteSocialAuthDataSource(
    private val httpClient: HttpClient,
) {
    suspend fun signInWithGoogle(
        idToken: String,
    ): Either<DataFailure.Network, SocialLoginResponse?> {
        var refreshToken: String? = null
        return safeCall<SocialLoginResponse> {
            val response = httpClient.post {
                this.url(constructRoute(route = "/v1/oauth2/google/id-token"))
                setBody(body = mapOf("idToken" to idToken))
            }
            refreshToken = response.getRefreshTokenFromCookies()

            response
        }.map {
            if (it.accessToken.isNotEmpty()) {
                it.copy(
                    refreshToken = refreshToken,
                )
            } else {
                null
            }
        }
    }
}
