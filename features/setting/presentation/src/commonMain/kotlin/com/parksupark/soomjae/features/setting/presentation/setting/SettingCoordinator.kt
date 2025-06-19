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

    fun handle(action: SettingAction) {
        when (action) {
            SettingAction.OnBackClick -> navigator.navigateBack()
        }
    }
}

@Composable
internal fun rememberSettingCoordinator(
    navigator: SettingNavigator,
    viewModel: SettingViewModel = koinViewModel(),
): SettingCoordinator = remember(viewModel) {
    SettingCoordinator(
        navigator = navigator,
        viewModel = viewModel,
    )
}
