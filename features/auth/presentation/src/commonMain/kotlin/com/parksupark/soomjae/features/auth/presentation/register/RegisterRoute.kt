package com.parksupark.soomjae.features.auth.presentation.register

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.parksupark.soomjae.features.auth.presentation.navigation.AuthNavigator

@Composable
fun RegisterRoute(
    navigator: AuthNavigator,
    coordinator: RegisterCoordinator = rememberRegisterCoordinator(navigator),
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
