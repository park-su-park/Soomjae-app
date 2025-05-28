package com.parksupark.soomjae.features.auth.presentation.register

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.parksupark.soomjae.features.auth.presentation.navigation.AuthNavigator

@Composable
fun RegisterRoute(
    navigator: AuthNavigator,
    coordinator: RegisterCoordinator = rememberRegisterCoordinator(navigator),
) {
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle(RegisterState())

    val actionsHandler: (RegisterAction) -> Unit = { action ->
        coordinator.handle(action)
    }

    RegisterScreen(
        state = uiState,
        onAction = actionsHandler,
    )
}
