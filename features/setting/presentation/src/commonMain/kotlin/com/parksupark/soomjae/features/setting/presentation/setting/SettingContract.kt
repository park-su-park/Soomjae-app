package com.parksupark.soomjae.features.setting.presentation.setting

class SettingState

internal sealed interface SettingAction {
    data object OnBackClick : SettingAction
}
