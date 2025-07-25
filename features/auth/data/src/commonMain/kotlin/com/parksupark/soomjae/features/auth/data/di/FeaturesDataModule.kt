package com.parksupark.soomjae.features.auth.data.di

import com.parksupark.soomjae.features.auth.data.AuthRepositoryImpl
import com.parksupark.soomjae.features.auth.data.datasources.remote.RemoteAuthDataSource
import com.parksupark.soomjae.features.auth.domain.AuthRepository
import com.parksupark.soomjae.features.auth.domain.UserDataValidator
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val featuresAuthDataModule = module {
    singleOf(::UserDataValidator)

    singleOf(::RemoteAuthDataSource)
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()
}
