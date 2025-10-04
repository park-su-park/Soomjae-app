package com.parksupark.soomjae.features.auth.presentation.email_verification

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.parksupark.soomjae.core.presentation.ui.ObserveAsEvents
import com.parksupark.soomjae.core.presentation.ui.components.showSnackbar
import com.parksupark.soomjae.features.auth.presentation.navigation.AuthNavigator
import kotlinx.coroutines.launch

@Composable
fun EmailVerificationRoute(
    navigator: AuthNavigator,
    coordinator: EmailVerificationCoordinator = rememberEmailVerifyCoordinator(navigator = navigator),
) {
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle()

    val actionsHandler: (EmailVerificationAction) -> Unit = { action ->
        coordinator.handle(action)
    }

    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    ObserveAsEvents(
        flow = coordinator.events,
        onEvent = { event ->
            when (event) {
                EmailVerificationEvent.VerificationSuccess -> navigator.navigateToRegisterDetail(uiState.email.text.trim().toString())

                is EmailVerificationEvent.Error -> {
                    val message = event.message
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(uiText = message, isError = true)
                    }
                }
            }
        },
    )

    EmailVerificationScreen(
        state = uiState,
        onAction = actionsHandler,
        snackbarHostState = snackbarHostState,
    )
}
