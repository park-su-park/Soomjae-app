package com.parksupark.soomjae.core.remote.networking

import com.parksupark.soomjae.core.domain.auth.datasources.SessionDataSource
import com.parksupark.soomjae.core.remote.dtos.response.RefreshTokenResponse
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.encodedPath
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import co.touchlab.kermit.Logger as Kermit

expect fun platformHttpClientEngine(): HttpClientEngineFactory<HttpClientEngineConfig>

internal class HttpClientFactory(
    private val sessionRepository: SessionDataSource,
) {
    fun build(): HttpClient = HttpClient(platformHttpClientEngine()) {
        install(ContentNegotiation) {
            json(
                json = Json {
                    ignoreUnknownKeys = true
                },
            )
        }
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Kermit.i(HTTPCLIENT_TAG) { message }
                }
            }
            level = LogLevel.ALL
        }
        install(Auth) {
            bearer {
                loadTokens {
                    val info = sessionRepository.get()

                    info?.accessToken?.let {
                        Kermit.d(HTTPCLIENT_TAG) { "Existing access token found" }
                        BearerTokens(
                            accessToken = it,
                            refreshToken = null,
                        )
                    }
                }
                refreshTokens {
                    val currentInfo = sessionRepository.get()
                    if (currentInfo == null) return@refreshTokens null

                    client.post<Unit, RefreshTokenResponse>(
                        route = "/v1/auth/refresh",
                        body = Unit,
                    ).fold(
                        ifLeft = {
                            Kermit.e(HTTPCLIENT_AUTH_TAG) { "Failed to refresh token: $it" }
                            null
                        },
                        ifRight = {
                            Kermit.d(HTTPCLIENT_AUTH_TAG) { "Successfully refreshed token" }
                            val newAccessToken = it.accessToken
                            sessionRepository.set(
                                currentInfo.copy(accessToken = newAccessToken),
                            )
                            BearerTokens(
                                accessToken = newAccessToken,
                                refreshToken = null,
                            )
                        },
                    )
                }
                sendWithoutRequest { request ->
                    !request.url.encodedPath.startsWith("/v1/oauth2/")
                }
            }
        }

        defaultRequest {
            contentType(ContentType.Application.Json)
        }
    }
}

private const val HTTPCLIENT_TAG = "HttpClient"
private const val HTTPCLIENT_AUTH_TAG = "HttpClientAuth"
