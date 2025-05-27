package com.parksupark.soomjae.features.auth.presentation.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun LoginRoute(coordinator: LoginCoordinator = rememberLoginCoordinator()) {
    val uiState by coordinator.uiStateFlow.collectAsState(LoginState())

    val actionsHandler: (LoginAction) -> Unit = { action ->
        coordinator.handle(action)
    }

    LoginScreen(
        state = uiState,
        onAction = actionsHandler,
    )
}
