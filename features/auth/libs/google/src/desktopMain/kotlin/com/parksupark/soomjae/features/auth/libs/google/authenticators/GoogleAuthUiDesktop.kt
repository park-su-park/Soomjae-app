package com.parksupark.soomjae.features.auth.libs.google.authenticators

import arrow.core.Either
import arrow.core.raise.either
import co.touchlab.kermit.Logger
import com.auth0.jwt.JWT
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.features.auth.libs.google.internal.BrowserOpener
import com.parksupark.soomjae.features.auth.libs.google.internal.GoogleAuthUrlBuilder
import com.parksupark.soomjae.features.auth.libs.google.internal.RedirectCallbackServer
import com.parksupark.soomjae.features.auth.libs.google.models.GoogleUser
import java.security.SecureRandom
import java.util.Base64

internal class GoogleAuthUiDesktop(
    private val urlBuilder: GoogleAuthUrlBuilder,
    private val browserOpener: BrowserOpener,
    private val redirectCallbackServer: RedirectCallbackServer,
) : GoogleAuthUi {
    override suspend fun getUser(scope: List<String>): Either<DataFailure.Credential, GoogleUser> = either {
        val responseType = "id_token token"
        val state = generateRandomString()
        val nonce = generateRandomString()

        val portResult = redirectCallbackServer.findAvailablePort()
        val port = portResult.bind()

        val redirectUri = buildRedirectUri(port)
        val googleAuthUrl = urlBuilder.buildAuthUrl(
            scopes = scope,
            responseType = responseType,
            redirectUri = redirectUri,
            state = state,
            nonce = nonce,
        )
        openAuthUrlInBrowser(googleAuthUrl)

        val loginResult = redirectCallbackServer.startServerAndAwaitTokens(
            state = state,
            redirectUriPath = "/$REDIRECT_URI_PATH",
            port = port,
        )
        val (idToken, accessToken) = loginResult.bind()

        return parseAndValidateJwt(idToken, nonce).map { user ->
            user.copy(
                idToken = idToken ?: "",
                accessToken = accessToken,
            )
        }
    }

    private fun buildRedirectUri(port: Int): String = "http://localhost:$port/$REDIRECT_URI_PATH"

    private fun openAuthUrlInBrowser(url: String) {
        browserOpener.openUrl(url)
    }

    private fun parseAndValidateJwt(
        idToken: String?,
        nonce: String,
    ): Either<DataFailure.Credential, GoogleUser> {
        if (idToken == null) return Either.Left(DataFailure.Credential.UNKNOWN)
        val jwt = JWT().decodeJwt(idToken)
        val email = jwt.getClaim("email").asString()
        val name = jwt.getClaim("name").asString()
        val picture = jwt.getClaim("picture").asString()
        val receivedNonce = jwt.getClaim("nonce").asString()
        if (receivedNonce != nonce) {
            Logger.e { "GoogleAuthUiProvider: Invalid nonce state: A login callback was received, but no login request was sent." }
            return Either.Left(DataFailure.Credential.NOT_FOUND)
        }
        return Either.Right(
            GoogleUser(
                idToken = idToken,
                accessToken = null,
                email = email,
                displayName = name ?: "",
                profilePicUrl = picture,
            ),
        )
    }

    companion object {
        private const val REDIRECT_URI_PATH = "callback"
    }
}

private fun generateRandomString(length: Int = 32): String {
    val secureRandom = SecureRandom()
    val stateBytes = ByteArray(length)
    secureRandom.nextBytes(stateBytes)
    return Base64.getUrlEncoder().withoutPadding().encodeToString(stateBytes)
}
