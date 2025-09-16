package com.parksupark.soomjae.features.auth.data.di

import com.parksupark.soomjae.core.data.datastore.SETTING_DATA_STORE
import com.parksupark.soomjae.features.auth.data.datasources.local.LocalAuthDataSource
import com.parksupark.soomjae.features.auth.data.datasources.remote.RemoteAuthDataSource
import com.parksupark.soomjae.features.auth.data.datasources.remote.RemoteSocialAuthDataSource
import com.parksupark.soomjae.features.auth.data.repositories.AuthRepositoryImpl
import com.parksupark.soomjae.features.auth.data.repositories.SocialAuthRepositoryImpl
import com.parksupark.soomjae.features.auth.domain.AuthRepository
import com.parksupark.soomjae.features.auth.domain.SocialAuthRepository
import com.parksupark.soomjae.features.auth.domain.UserDataValidator
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

private val authModule = module {
    singleOf(::RemoteAuthDataSource)
    single { LocalAuthDataSource(get(named(SETTING_DATA_STORE))) }

    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()
}

private val socialAuthModule = module {
    singleOf(::RemoteSocialAuthDataSource)
    singleOf(::SocialAuthRepositoryImpl).bind<SocialAuthRepository>()
}

val featuresAuthDataModule = module {
    singleOf(::UserDataValidator)

    includes(authModule, socialAuthModule)
}
