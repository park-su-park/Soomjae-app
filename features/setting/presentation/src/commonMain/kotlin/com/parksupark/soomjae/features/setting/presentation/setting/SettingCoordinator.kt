package com.parksupark.soomjae.features.setting.presentation.setting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.koin.compose.viewmodel.koinViewModel

internal class SettingCoordinator(
    val viewModel: SettingViewModel,
) {
    val screenStateFlow = viewModel.stateFlow

    fun handle(action: SettingAction) {
        when (action) {
            SettingAction.OnBackClick -> {
            }
        }
    }
}

@Composable
internal fun rememberSettingCoordinator(viewModel: SettingViewModel = koinViewModel()): SettingCoordinator = remember(viewModel) {
    SettingCoordinator(
        viewModel = viewModel,
    )
}
