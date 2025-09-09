package com.parksupark.soomjae.features.auth.libs.google.di

import com.parksupark.soomjae.features.auth.libs.google.authenticators.GoogleAuthProvider
import com.parksupark.soomjae.features.auth.libs.google.authenticators.GoogleAuthUi
import com.parksupark.soomjae.features.auth.libs.google.di.authenticators.GoogleAuthProviderIOS
import com.parksupark.soomjae.features.auth.libs.google.di.authenticators.GoogleAuthUiIOS
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformGoogleAuthModule = module {
    factoryOf(::GoogleAuthUiIOS) bind GoogleAuthUi::class
    factoryOf(::GoogleAuthProviderIOS) bind GoogleAuthProvider::class
}
