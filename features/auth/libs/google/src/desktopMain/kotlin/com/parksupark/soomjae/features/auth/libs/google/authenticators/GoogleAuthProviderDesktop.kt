package com.parksupark.soomjae.features.auth.libs.google.authenticators

import androidx.compose.runtime.Composable
import com.parksupark.soomjae.core.common.coroutines.SoomjaeDispatcher
import com.parksupark.soomjae.features.auth.libs.google.internal.BrowserOpener
import com.parksupark.soomjae.features.auth.libs.google.internal.GoogleAuthUrlBuilder
import com.parksupark.soomjae.features.auth.libs.google.internal.RedirectCallbackServer

internal class GoogleAuthProviderDesktop(
    private val dispatcher: SoomjaeDispatcher,
    private val urlBuilder: GoogleAuthUrlBuilder,
    private val browserOpener: BrowserOpener,
    private val redirectCallbackServer: RedirectCallbackServer,
) : GoogleAuthProvider {
    @Composable
    override fun getUiProvider(): GoogleAuthUi = GoogleAuthUiDesktop(
        urlBuilder = urlBuilder,
        browserOpener = browserOpener,
        redirectCallbackServer = redirectCallbackServer,
    )
}
