package com.parksupark.soomjae.core.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

fun createSessionDataStore(context: Context): DataStore<Preferences> = createSessionDataStore(
    producePath = {
        context.filesDir.resolve(SESSION_DATA_STORE).absolutePath
    },
)
