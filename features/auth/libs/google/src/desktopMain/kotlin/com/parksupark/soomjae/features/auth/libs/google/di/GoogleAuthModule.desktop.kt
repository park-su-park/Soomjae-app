package com.parksupark.soomjae.features.auth.libs.google.di

import com.parksupark.soomjae.features.auth.libs.google.authenticators.GoogleAuthProvider
import com.parksupark.soomjae.features.auth.libs.google.authenticators.GoogleAuthProviderDesktop
import com.parksupark.soomjae.features.auth.libs.google.internal.BrowserOpener
import com.parksupark.soomjae.features.auth.libs.google.internal.GoogleAuthUrlBuilder
import com.parksupark.soomjae.features.auth.libs.google.internal.RedirectCallbackServer
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformGoogleAuthModule = module {
    singleOf(::GoogleAuthUrlBuilder)
    singleOf(::BrowserOpener)
    singleOf(::RedirectCallbackServer)
    factoryOf(::GoogleAuthProviderDesktop) bind GoogleAuthProvider::class
}
