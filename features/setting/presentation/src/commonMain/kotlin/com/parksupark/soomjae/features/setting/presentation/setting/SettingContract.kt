package com.parksupark.soomjae.features.setting.presentation.setting

import com.parksupark.soomjae.core.domain.model.ColorTheme

data class SettingState(
    val colorTheme: ColorTheme = ColorTheme.SYSTEM,
)

internal sealed interface SettingAction {
    data object OnBackClick : SettingAction

    data class OnThemeChange(val theme: ColorTheme) : SettingAction
}
