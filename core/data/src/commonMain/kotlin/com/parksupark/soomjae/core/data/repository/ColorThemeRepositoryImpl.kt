package com.parksupark.soomjae.core.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.parksupark.soomjae.core.common.theme.ColorTheme
import com.parksupark.soomjae.core.data.datastore.SettingDataStoreKey
import com.parksupark.soomjae.core.domain.repository.ColorThemeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class ColorThemeRepositoryImpl(
    private val dataStore: DataStore<Preferences>,
) : ColorThemeRepository {
    override fun getColorThemeStream(): Flow<ColorTheme> = dataStore.data.map { settings ->
        settings[SettingDataStoreKey.COLOR_THEME]?.toColorTheme() ?: ColorTheme.SYSTEM
    }

    override suspend fun setColorTheme(theme: ColorTheme) {
        dataStore.edit { settings ->
            settings[SettingDataStoreKey.COLOR_THEME] = theme.name
        }
    }
}

private fun String.toColorTheme(): ColorTheme? = ColorTheme.entries.firstOrNull { it.name == this }
