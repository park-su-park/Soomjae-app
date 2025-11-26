package com.parksupark.soomjae.core.data.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.parksupark.soomjae.core.data.datastore.SESSION_DATA_STORE
import com.parksupark.soomjae.core.data.datastore.SETTING_DATA_STORE
import com.parksupark.soomjae.core.data.datastore.createSessionDataStore
import com.parksupark.soomjae.core.data.datastore.createSettingDataStore
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

actual val platformCoreDataModule: Module = module {
    single<DataStore<Preferences>>(named(SESSION_DATA_STORE)) { createSessionDataStore() }
    single<DataStore<Preferences>>(named(SETTING_DATA_STORE)) { createSettingDataStore() }
}
