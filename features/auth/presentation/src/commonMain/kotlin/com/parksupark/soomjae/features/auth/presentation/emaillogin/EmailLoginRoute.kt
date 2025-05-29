package com.parksupark.soomjae.features.auth.presentation.emaillogin

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.parksupark.soomjae.core.presentation.ui.components.SoomjaeSnackbarHost
import com.parksupark.soomjae.features.auth.presentation.navigation.AuthNavigator

@Composable
fun EmailLoginRoute(
    navigator: AuthNavigator,
    coordinator: EmailLoginCoordinator = rememberEmailLoginCoordinator(navigator),
) {
    val uiState by coordinator.screenStateFlow.collectAsState(EmailLoginState())

    val actionsHandler: (EmailLoginAction) -> Unit = { action ->
        coordinator.handle(action)
    }

    val snackbarHostState = remember { SnackbarHostState() }

    EmailLoginScreen(
        state = uiState,
        onAction = actionsHandler,
        snackbarHost = { SoomjaeSnackbarHost(snackbarHostState) },
    )
}
