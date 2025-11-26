package com.parksupark.soomjae.features.auth.libs.google.authenticators

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.CredentialManager
import com.parksupark.soomjae.features.auth.libs.google.models.GoogleAuthCredential

internal class GoogleAuthProviderAndroid(
    private val credentials: GoogleAuthCredential,
    private val credentialManager: CredentialManager,
) : GoogleAuthProvider {
    @Composable
    override fun getUiProvider(): GoogleAuthUi {
        val activityContext = LocalContext.current

        return GoogleAuthUiAndroid(
            context = activityContext,
            credentials = credentials,
            credentialManager = credentialManager,
        )
    }
}
