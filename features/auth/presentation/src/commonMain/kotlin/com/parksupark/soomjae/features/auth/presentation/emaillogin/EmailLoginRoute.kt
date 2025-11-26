package com.parksupark.soomjae.features.auth.presentation.emaillogin

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.parksupark.soomjae.core.presentation.ui.ObserveAsEvents
import com.parksupark.soomjae.core.presentation.ui.components.SoomjaeSnackbarHost
import com.parksupark.soomjae.core.presentation.ui.components.showSnackbar
import com.parksupark.soomjae.features.auth.presentation.navigation.AuthNavigator
import kotlinx.coroutines.launch

@Composable
fun EmailLoginRoute(
    navigator: AuthNavigator,
    coordinator: EmailLoginCoordinator = rememberEmailLoginCoordinator(navigator),
) {
    val uiState by coordinator.screenStateFlow.collectAsState(EmailLoginState())
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val actionsHandler: (EmailLoginAction) -> Unit = { action ->
        coordinator.handle(action)
    }

    ObserveAsEvents(
        flow = coordinator.eventsFlow,
        onEvent = { event ->
            when (event) {
                is EmailLoginEvent.Error ->
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(event.message, isError = true)
                    }

                EmailLoginEvent.LoginSuccess ->
                    navigator.popUpAuthGraph()
            }
        },
    )

    EmailLoginScreen(
        state = uiState,
        onAction = actionsHandler,
        snackbarHost = { SoomjaeSnackbarHost(snackbarHostState) },
    )
}
