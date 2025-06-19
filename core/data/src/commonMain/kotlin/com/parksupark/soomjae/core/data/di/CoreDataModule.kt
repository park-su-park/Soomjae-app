package com.parksupark.soomjae.core.data.di

import com.parksupark.soomjae.core.data.auth.repositories.SessionRepositoryImpl
import com.parksupark.soomjae.core.data.datastore.SESSION_DATA_STORE
import com.parksupark.soomjae.core.data.datastore.SETTING_DATA_STORE
import com.parksupark.soomjae.core.data.repository.ColorThemeRepositoryImpl
import com.parksupark.soomjae.core.domain.auth.repositories.SessionRepository
import com.parksupark.soomjae.core.domain.repository.ColorThemeRepository
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

internal expect val platformCoreDataModule: Module

val coreDataModule = module {
    includes(platformCoreDataModule)

    @Suppress("UndeclaredKoinUsage")
    single {
        SessionRepositoryImpl(get(named(SESSION_DATA_STORE)))
    }.bind<SessionRepository>()

    @Suppress("UndeclaredKoinUsage")
    single {
        ColorThemeRepositoryImpl(get(named(SETTING_DATA_STORE)))
    }.bind<ColorThemeRepository>()
}
