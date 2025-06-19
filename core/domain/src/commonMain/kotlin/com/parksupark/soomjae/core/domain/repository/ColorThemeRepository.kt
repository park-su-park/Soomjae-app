package com.parksupark.soomjae.core.domain.repository

import com.parksupark.soomjae.core.domain.model.ColorTheme
import kotlinx.coroutines.flow.Flow

interface ColorThemeRepository {
    fun getColorThemeStream(): Flow<ColorTheme>

    suspend fun setColorTheme(theme: ColorTheme)
}
