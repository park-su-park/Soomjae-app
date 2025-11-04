package com.parksupark.soomjae.features.setting.presentation.setting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.parksupark.soomjae.features.setting.presentation.navigation.SettingNavigator
import org.koin.compose.viewmodel.koinViewModel

internal class SettingCoordinator(
    private val navigator: SettingNavigator,
    val viewModel: SettingViewModel,
) {
    val screenStateFlow = viewModel.stateFlow
    val event = viewModel.events

    fun handle(action: SettingAction) {
        when (action) {
            is SettingAction.OnBackClick -> navigator.navigateBack()
            is SettingAction.OnThemeChange -> viewModel.changeColorTheme(action.theme)
            is SettingAction.OnLogoutClick -> viewModel.logout()
        }
    }
}

@Composable
internal fun rememberSettingCoordinator(
    navigator: SettingNavigator,
    viewModel: SettingViewModel = koinViewModel(),
): SettingCoordinator = remember(navigator, viewModel) {
    SettingCoordinator(
        navigator = navigator,
        viewModel = viewModel,
    )
}
