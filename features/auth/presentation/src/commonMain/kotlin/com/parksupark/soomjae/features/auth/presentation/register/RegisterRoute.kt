package com.parksupark.soomjae.features.auth.presentation.register

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.parksupark.soomjae.core.presentation.ui.ObserveAsEvents
import com.parksupark.soomjae.core.presentation.ui.components.SoomjaeSnackbarHost
import com.parksupark.soomjae.core.presentation.ui.components.showSnackbar
import com.parksupark.soomjae.features.auth.presentation.navigation.AuthNavigator
import com.parksupark.soomjae.features.auth.presentation.register.RegisterEvent.Error
import com.parksupark.soomjae.features.auth.presentation.register.RegisterEvent.RegistrationSuccess
import kotlinx.coroutines.launch

@Composable
fun RegisterRoute(
    navigator: AuthNavigator,
    coordinator: RegisterCoordinator = rememberRegisterCoordinator(navigator),
) {
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle(RegisterState())
    val snackbarHostState = remember { SnackbarHostState() }

    val actionsHandler: (RegisterAction) -> Unit = { action ->
        coordinator.handle(action)
    }

    val registerScope = rememberCoroutineScope()
    ObserveAsEvents(
        flow = coordinator.eventChannelFlow,
        onEvent = { event ->
            when (event) {
                is Error -> registerScope.launch {
                    snackbarHostState.showSnackbar(event.error, isError = true)
                }

                is RegistrationSuccess -> coordinator.navigator.navigateToEmailLogin()
            }
        },
    )

    RegisterScreen(
        state = uiState,
        onAction = actionsHandler,
        snackbarHost = { SoomjaeSnackbarHost(snackbarHostState) },
    )
}
