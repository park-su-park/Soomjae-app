package com.parksupark.soomjae.features.auth.libs.google.di

import androidx.credentials.CredentialManager
import com.parksupark.soomjae.features.auth.libs.google.authenticators.GoogleAuthProvider
import com.parksupark.soomjae.features.auth.libs.google.authenticators.GoogleAuthProviderAndroid
import com.parksupark.soomjae.features.auth.libs.google.authenticators.GoogleAuthUi
import com.parksupark.soomjae.features.auth.libs.google.authenticators.GoogleAuthUiAndroid
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformGoogleAuthModule = module {
    factoryOf(CredentialManager::create) bind CredentialManager::class
    factoryOf(::GoogleAuthProviderAndroid) bind GoogleAuthProvider::class
    factoryOf(::GoogleAuthUiAndroid) bind GoogleAuthUi::class
}
