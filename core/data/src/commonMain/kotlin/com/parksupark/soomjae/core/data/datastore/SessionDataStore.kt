package com.parksupark.soomjae.core.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import okio.Path.Companion.toPath

internal const val SESSION_DATA_STORE = "session.preferences_pb"

fun createSessionDataStore(producePath: () -> String): DataStore<Preferences> =
    PreferenceDataStoreFactory.createWithPath(
        produceFile = { producePath().toPath() },
    )

internal object SessionDataStoreKey {
    val SESSION = stringPreferencesKey("session")
}
