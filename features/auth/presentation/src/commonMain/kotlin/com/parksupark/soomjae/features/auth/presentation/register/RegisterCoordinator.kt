package com.parksupark.soomjae.features.auth.presentation.register

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.parksupark.soomjae.features.auth.presentation.navigation.AuthNavigator
import org.koin.compose.viewmodel.koinViewModel

class RegisterCoordinator(
    val navigator: AuthNavigator,
    val viewModel: RegisterViewModel,
) {
    val screenStateFlow = viewModel.uiState
    val eventChannelFlow = viewModel.eventChannel

    fun handle(action: RegisterAction) {
        when (action) {
            is RegisterAction.OnBackClick -> navigator.navigateBack()

            is RegisterAction.OnLoginClick -> navigator.navigateToEmailLogin()

            is RegisterAction.OnRegisterClick -> viewModel.register()
        }
    }
}

@Composable
fun rememberRegisterCoordinator(
    navigator: AuthNavigator,
    viewModel: RegisterViewModel = koinViewModel(),
): RegisterCoordinator = remember(viewModel) {
    RegisterCoordinator(
        navigator = navigator,
        viewModel = viewModel,
    )
}
