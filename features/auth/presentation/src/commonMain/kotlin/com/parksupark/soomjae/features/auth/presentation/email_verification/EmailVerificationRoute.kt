package com.parksupark.soomjae.features.auth.presentation.email_verification

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.parksupark.soomjae.features.auth.presentation.navigation.AuthNavigator

@Composable
fun EmailVerificationRoute(
    navigator: AuthNavigator,
    coordinator: EmailVerificationCoordinator = rememberEmailVerifyCoordinator(navigator = navigator),
) {
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle()

    val actionsHandler: (EmailVerificationAction) -> Unit = { action ->
        coordinator.handle(action)
    }

    EmailVerificationScreen(
        state = uiState,
        onAction = actionsHandler,
    )
}
