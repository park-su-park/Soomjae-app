package com.parksupark.soomjae.features.auth.data.datasources.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.parksupark.soomjae.core.data.datastore.SettingDataStoreKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class LocalLastLoginDataSource(
    private val dataStore: DataStore<Preferences>,
) {
    val recentLoginFlow: Flow<LoginTypeLocal?> = dataStore.data
        .map { preferences ->
            preferences[SettingDataStoreKey.RECENT_LOGIN_TYPE]?.let { loginTypeString ->
                runCatching { LoginTypeLocal.valueOf(loginTypeString) }.getOrNull()
            }
        }

    suspend fun saveRecentLogin(type: LoginTypeLocal) {
        dataStore.edit { preferences ->
            preferences[SettingDataStoreKey.RECENT_LOGIN_TYPE] = type.name
        }
    }
}
