package com.parksupark.soomjae.core.data.di

import com.parksupark.soomjae.core.data.datastore.SESSION_DATA_STORE
import com.parksupark.soomjae.core.data.datastore.createSessionDataStore
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

actual val platformCoreDataModule: Module = module {
    single(named(SESSION_DATA_STORE)) { createSessionDataStore() }
}
