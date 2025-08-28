package com.parksupark.soomjae.features.auth.libs.google.internal

import arrow.core.Either
import co.touchlab.kermit.Logger
import com.parksupark.soomjae.core.domain.failures.DataFailure
import io.ktor.http.ContentType
import io.ktor.server.engine.embeddedServer
import io.ktor.server.html.respondHtml
import io.ktor.server.netty.Netty
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import java.net.ServerSocket
import kotlinx.coroutines.CompletableDeferred
import kotlinx.html.body
import kotlinx.html.script
import kotlinx.html.unsafe
import org.intellij.lang.annotations.Language

internal class RedirectCallbackServer {

    private val recommendedPorts = listOf(51888, 53999, 55123)

    fun findAvailablePort(): Either<DataFailure.Credential, Int> {
        for (port in recommendedPorts) {
            if (isPortAvailable(port)) {
                return Either.Right(port)
            }
        }
        return Either.Left(DataFailure.Credential.PORT_UNAVAILABLE)
    }

    suspend fun startServerAndAwaitTokens(
        state: String,
        redirectUriPath: String = "/callback",
        port: Int,
    ): Either<DataFailure.Credential, Pair<String?, String?>> = try {
        val tokens = runServer(state, redirectUriPath, port)
        Either.Right(tokens)
    } catch (e: Exception) {
        Logger.e(e) { "Failed to start server on port $port" }
        Either.Left(DataFailure.Credential.PORT_UNAVAILABLE)
    }

    private fun isPortAvailable(port: Int): Boolean = try {
        ServerSocket(port).close()
        true
    } catch (e: Exception) {
        false
    }

    private suspend fun runServer(
        state: String,
        redirectUriPath: String,
        port: Int,
    ): Pair<String?, String?> {
        Logger.d { "port: $port" }
        val tokenPairDeferred = CompletableDeferred<Pair<String?, String?>>()

        // Note: The port used by the server needs to be part of the REDIRECT_URI
        // registered with Google. If findAvailablePort() is used, this needs to be dynamic.
        // For simplicity, if a fixed port is used in REDIRECT_URI, pass it here.
        // val actualRedirectUri = "http://localhost:$port$redirectUriPath"

        @Language("JavaScript")
        val jsCode =
            """
            var fragment = window.location.hash;
            if (fragment) {
                var params = new URLSearchParams(fragment.substring(1));
                var idToken = params.get('id_token');
                var accessToken = params.get('access_token');
                var receivedState = params.get('state');
                var expectedState = '$state';
                if (receivedState === expectedState) {
                    // Pass tokens to the /token endpoint
                    window.location.href = '$redirectUriPath/token?' +
                        (idToken ? 'id_token=' + encodeURIComponent(idToken) : '') +
                        (idToken && accessToken ? '&' : '') +
                        (accessToken ? 'access_token=' + encodeURIComponent(accessToken) : '');
                } else {
                    console.error('State does not match! Possible CSRF attack. Received: ' + receivedState + ', Expected: ' + expectedState);
                    window.location.href = '$redirectUriPath/token?error=state_mismatch';
                }
            } else {
                // No fragment, could be an error from Google directly in query params
                var queryParams = new URLSearchParams(window.location.search);
                var error = queryParams.get('error');
                if (error) {
                     window.location.href = '$redirectUriPath/token?error=' + encodeURIComponent(error);
                } else {
                     window.location.href = '$redirectUriPath/token?error=no_fragment_or_query_error';
                }
            }
            """.trimIndent()

        val server = embeddedServer(Netty, port = port) {
            routing {
                get(redirectUriPath) {
                    call.respondHtml {
                        body { script { unsafe { +jsCode } } }
                    }
                }
                get("$redirectUriPath/token") {
                    val idToken = call.request.queryParameters["id_token"]
                    val accessToken = call.request.queryParameters["access_token"]
                    val error = call.request.queryParameters["error"]

                    if (error != null) {
                        call.respondText(
                            "Authorization failed: $error. You can close this window.",
                            contentType = ContentType.Text.Plain,
                        )
                        tokenPairDeferred.complete(Pair(null, null))
                    } else if (!idToken.isNullOrEmpty() || !accessToken.isNullOrEmpty()) {
                        call.respondText(
                            "Authorization is complete. You can close this window and return to the application.",
                            contentType = ContentType.Text.Plain,
                        )
                        tokenPairDeferred.complete(Pair(idToken, accessToken))
                    } else {
                        call.respondText(
                            "Authorization failed: No token received. You can close this window.",
                            contentType = ContentType.Text.Plain,
                        )
                        tokenPairDeferred.complete(Pair(null, null))
                    }
                }
            }
        }.start(wait = false)

        try {
            return tokenPairDeferred.await()
        } finally {
            server.stop(500, 1000) // Reduced grace period for faster shutdown
        }
    }
}
