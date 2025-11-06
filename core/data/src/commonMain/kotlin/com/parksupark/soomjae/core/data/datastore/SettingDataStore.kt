package com.parksupark.soomjae.core.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import okio.Path.Companion.toPath

const val SETTING_DATA_STORE = "setting.preferences_pb"

internal fun createSettingDataStore(producePath: () -> String): DataStore<Preferences> =
    PreferenceDataStoreFactory.createWithPath(
        produceFile = { producePath().toPath() },
    )

object SettingDataStoreKey {
    val COLOR_THEME = stringPreferencesKey("color_theme")
    val SAVED_EMAIL = stringPreferencesKey("saved_email")
    val RECENT_LOGIN_TYPE = stringPreferencesKey("recent_login_type")
}
