package com.parksupark.soomjae.core.data.di

import com.parksupark.soomjae.core.data.auth.repositories.SessionRepositoryImpl
import com.parksupark.soomjae.core.data.datastore.SESSION_DATA_STORE
import com.parksupark.soomjae.core.domain.auth.repositories.SessionRepository
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal expect val platformCoreDataModule: Module

val coreDataModule = module {
    includes(platformCoreDataModule)

    @Suppress("UndeclaredKoinUsage")
    single<SessionRepository> {
        SessionRepositoryImpl(get(named(SESSION_DATA_STORE)))
    }
}
