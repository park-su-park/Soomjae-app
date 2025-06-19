package com.parksupark.soomjae.features.setting.presentation.setting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.parksupark.soomjae.features.setting.presentation.navigation.SettingNavigator

@Composable
internal fun SettingRoute(
    navigator: SettingNavigator,
    coordinator: SettingCoordinator = rememberSettingCoordinator(navigator),
) {
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle()

    val actionsHandler: (SettingAction) -> Unit = { action ->
        coordinator.handle(action)
    }

    SettingScreen(
        state = uiState,
        onAction = actionsHandler,
    )
}
