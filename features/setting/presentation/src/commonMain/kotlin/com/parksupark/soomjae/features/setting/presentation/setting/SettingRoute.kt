package com.parksupark.soomjae.features.setting.presentation.setting

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.parksupark.soomjae.core.presentation.ui.ObserveAsEvents
import com.parksupark.soomjae.core.presentation.ui.components.showSnackbar
import com.parksupark.soomjae.features.setting.presentation.navigation.SettingNavigator
import kotlinx.coroutines.launch

@Composable
internal fun SettingRoute(
    navigator: SettingNavigator,
    coordinator: SettingCoordinator = rememberSettingCoordinator(navigator),
) {
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    val actionsHandler: (SettingAction) -> Unit = { action ->
        coordinator.handle(action)
    }

    val scope = rememberCoroutineScope()
    ObserveAsEvents(
        flow = coordinator.event,
    ) { event ->
        when (event) {
            is SettingEvent.OnLogoutSuccess -> {
                coordinator.navigateOnLogoutSuccess()
            }

            is SettingEvent.OnLogoutFailure -> {
                scope.launch {
                    snackbarHostState.showSnackbar(uiText = event.error, isError = true)
                }
            }
        }
    }

    SettingScreen(
        state = uiState,
        onAction = actionsHandler,
        snackbarHostState = snackbarHostState,
    )
}
