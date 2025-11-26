package com.parksupark.soomjae.core.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

internal fun createSettingDataStore(context: Context): DataStore<Preferences> =
    createSettingDataStore(
        producePath = {
            context.filesDir.resolve(SETTING_DATA_STORE).absolutePath
        },
    )
