package com.parksupark.soomjae.core.data.di

import com.parksupark.soomjae.core.data.auth.repositories.SessionRepositoryImpl
import com.parksupark.soomjae.core.data.datasource.PreferenceSessionDataSource
import com.parksupark.soomjae.core.data.datastore.SESSION_DATA_STORE
import com.parksupark.soomjae.core.data.datastore.SETTING_DATA_STORE
import com.parksupark.soomjae.core.data.logging.KermitLogger
import com.parksupark.soomjae.core.data.repository.ColorThemeRepositoryImpl
import com.parksupark.soomjae.core.domain.auth.datasources.SessionDataSource
import com.parksupark.soomjae.core.domain.auth.repositories.SessionRepository
import com.parksupark.soomjae.core.domain.logging.SjLogger
import com.parksupark.soomjae.core.domain.repository.ColorThemeRepository
import org.koin.core.annotation.Single
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.ksp.generated.module
import org.koin.core.annotation.Module as AnnotationModule

internal expect val platformCoreDataModule: Module

@AnnotationModule
internal object LoggingModule {
    @Single
    fun providesSjLogger(): SjLogger = KermitLogger
}

val coreDataModule = module {
    includes(platformCoreDataModule, LoggingModule.module)

    single {
        PreferenceSessionDataSource(
            get(named(SESSION_DATA_STORE)),
        )
    }.bind<SessionDataSource>()

    single {
        SessionRepositoryImpl(
            sessionDataSource = get(),
            httpClient = get(),
        )
    }.bind<SessionRepository>()

    single {
        ColorThemeRepositoryImpl(get(named(SETTING_DATA_STORE)))
    }.bind<ColorThemeRepository>()
}
