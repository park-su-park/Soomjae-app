package com.parksupark.soomjae.features.auth.presentation.login

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.parksupark.soomjae.core.presentation.ui.ObserveAsEvents
import com.parksupark.soomjae.core.presentation.ui.components.SoomjaeSnackbarHost
import com.parksupark.soomjae.core.presentation.ui.components.showSnackbar
import com.parksupark.soomjae.features.auth.libs.google.authenticators.GoogleAuthProvider
import com.parksupark.soomjae.features.auth.presentation.navigation.AuthNavigator
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
internal fun LoginRoute(
    navigator: AuthNavigator,
    coordinator: LoginCoordinator = rememberLoginCoordinator(navigator),
    googleAuthProvider: GoogleAuthProvider = koinInject(),
) {
    val uiState by coordinator.uiStateFlow.collectAsState(LoginState())

    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    ObserveAsEvents(
        flow = coordinator.events,
    ) { event ->
        when (event) {
            is LoginEvent.Error -> coroutineScope.launch {
                snackbarHostState.showSnackbar(uiText = event.message, isError = true)
            }

            LoginEvent.OnLoginSuccess -> navigator.navigateBack()
        }
    }

    val actionsHandler: (LoginAction) -> Unit = { action ->
        coordinator.handle(action)
    }

    LoginScreen(
        state = uiState,
        onAction = actionsHandler,
        snackbarHost = { SoomjaeSnackbarHost(hostState = snackbarHostState) },
        getGoogleAuthUi = { googleAuthProvider.getUiProvider() },
    )
}
