package com.parksupark.soomjae.features.setting.presentation.setting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
internal fun SettingRoute(coordinator: SettingCoordinator = rememberSettingCoordinator()) {
    val uiState by coordinator.screenStateFlow.collectAsState(SettingState())

    val actionsHandler: (SettingAction) -> Unit = { action ->
        coordinator.handle(action)
    }

    SettingScreen(
        state = uiState,
        onAction = actionsHandler,
    )
}
