package com.parksupark.soomjae.features.auth.libs.google.di

import SoomJae.features.auth.libs.google.BuildConfig
import com.parksupark.soomjae.features.auth.libs.google.models.GoogleAuthCredential
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformGoogleAuthModule: Module

private val sharedGoogleAuthModule = module {
    single { GoogleAuthCredential(BuildConfig.OAUTH_GOOGLE_SERVER_CLIENT_ID) }
}

val googleAuthModule = module {
    includes(platformGoogleAuthModule, sharedGoogleAuthModule)
}
