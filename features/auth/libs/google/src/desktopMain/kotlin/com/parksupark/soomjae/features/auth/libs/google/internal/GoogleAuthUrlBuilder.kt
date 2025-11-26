package com.parksupark.soomjae.features.auth.libs.google.internal

import com.parksupark.soomjae.features.auth.libs.google.models.GoogleAuthCredential
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

internal class GoogleAuthUrlBuilder(private val credential: GoogleAuthCredential) {
    fun buildAuthUrl(
        scopes: List<String>,
        responseType: String,
        redirectUri: String,
        state: String,
        nonce: String,
    ): String {
        val params = linkedMapOf(
            "client_id" to credential.serverId,
            "redirect_uri" to redirectUri,
            "response_type" to responseType,
            "scope" to scopes.joinToString(" "),
            "nonce" to nonce,
            "state" to state,
        )
        val query = params.entries.joinToString("&") { (k, v) ->
            "${encodeUrl(k)}=${encodeUrl(v)}"
        }

        return "https://accounts.google.com/o/oauth2/v2/auth?$query"
    }

    private fun encodeUrl(value: String): String =
        URLEncoder.encode(value, StandardCharsets.UTF_8.toString())
}
