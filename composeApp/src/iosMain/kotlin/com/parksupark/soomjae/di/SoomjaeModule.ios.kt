package com.parksupark.soomjae.di

import com.parksupark.soomjae.IosGoogleAuthService
import com.parksupark.soomjae.features.auth.libs.google.di.authenticators.GoogleAuthService
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformAppModule: Module = module {
    factory { IosGoogleAuthService() }.bind<GoogleAuthService>()
}
