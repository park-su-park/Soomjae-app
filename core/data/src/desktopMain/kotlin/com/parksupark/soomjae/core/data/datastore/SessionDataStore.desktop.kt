package com.parksupark.soomjae.core.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

fun createSessionDataStore(): DataStore<Preferences> = createSessionDataStore(
    producePath = { SESSION_DATA_STORE },
)
