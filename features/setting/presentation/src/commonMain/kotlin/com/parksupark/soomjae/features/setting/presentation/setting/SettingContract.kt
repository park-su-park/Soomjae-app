package com.parksupark.soomjae.features.setting.presentation.setting

import com.parksupark.soomjae.core.common.theme.ColorTheme
import com.parksupark.soomjae.core.presentation.ui.utils.UiText

data class SettingState(
    val colorTheme: ColorTheme = ColorTheme.SYSTEM,
    val isUserLoggedIn: Boolean = false,
)

internal sealed interface SettingAction {
    data object OnBackClick : SettingAction

    data class OnThemeChange(val theme: ColorTheme) : SettingAction

    data object OnLogoutClick : SettingAction

    data object OnLoginClick : SettingAction
}

internal sealed interface SettingEvent {
    data object OnLogoutSuccess : SettingEvent

    data class OnLogoutFailure(val error: UiText) : SettingEvent
}
