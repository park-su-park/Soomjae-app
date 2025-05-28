package com.parksupark.soomjae.features.auth.presentation.register

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun RegisterRoute(
    onNavigateUp: () -> Unit,
    coordinator: RegisterCoordinator = rememberRegisterCoordinator(onNavigateUp),
) {
    val uiState by coordinator.screenStateFlow.collectAsState(RegisterState())

    val actionsHandler: (RegisterAction) -> Unit = { action ->
        coordinator.handle(action)
    }

    RegisterScreen(
        state = uiState,
        onAction = actionsHandler,
    )
}
