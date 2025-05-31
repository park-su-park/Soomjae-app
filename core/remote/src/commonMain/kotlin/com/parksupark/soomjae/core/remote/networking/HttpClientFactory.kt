package com.parksupark.soomjae.core.remote.networking

import com.parksupark.soomjae.core.domain.auth.repositories.SessionRepository
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
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import co.touchlab.kermit.Logger as Kermit

expect fun platformHttpClientEngine(): HttpClientEngineFactory<HttpClientEngineConfig>

internal class HttpClientFactory(
    private val sessionRepository: SessionRepository,
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
                    BearerTokens(
                        accessToken = info?.accessToken ?: "",
                        refreshToken = null,
                    )
                }
            }
        }

        defaultRequest {
            contentType(ContentType.Application.Json)
        }
    }
}

private const val HTTPCLIENT_TAG = "HttpClient"
