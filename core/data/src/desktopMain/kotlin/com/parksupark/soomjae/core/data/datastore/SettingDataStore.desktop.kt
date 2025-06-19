package com.parksupark.soomjae.core.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

internal fun createSettingDataStore(): DataStore<Preferences> = createSettingDataStore(
    producePath = { SETTING_DATA_STORE },
)
