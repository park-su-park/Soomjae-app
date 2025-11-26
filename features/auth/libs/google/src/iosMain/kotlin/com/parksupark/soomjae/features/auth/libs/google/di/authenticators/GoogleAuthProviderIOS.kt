package com.parksupark.soomjae.features.auth.libs.google.di.authenticators

import androidx.compose.runtime.Composable
import com.parksupark.soomjae.core.common.coroutines.SoomjaeDispatcher
import com.parksupark.soomjae.features.auth.libs.google.authenticators.GoogleAuthProvider
import com.parksupark.soomjae.features.auth.libs.google.authenticators.GoogleAuthUi

internal class GoogleAuthProviderIOS(
    private val service: GoogleAuthService,
    private val dispatcher: SoomjaeDispatcher,
) : GoogleAuthProvider {
    @Composable
    override fun getUiProvider(): GoogleAuthUi = GoogleAuthUiIOS(
        service = service,
        dispatcher = dispatcher,
    )
}
