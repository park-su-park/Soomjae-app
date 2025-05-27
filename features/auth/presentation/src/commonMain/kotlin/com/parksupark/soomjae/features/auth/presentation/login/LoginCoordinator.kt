package com.parksupark.soomjae.features.auth.presentation.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.koin.compose.viewmodel.koinViewModel

class LoginCoordinator(
    val viewModel: LoginViewModel,
) {
    val uiStateFlow = viewModel.uiState

    fun handle(action: LoginAction) {
        when (action) {
            LoginAction.OnClick -> {}
            LoginAction.OnEmailLoginClick -> {}
            LoginAction.OnRegisterClick -> {}
        }
    }
}

@Composable
fun rememberLoginCoordinator(viewModel: LoginViewModel = koinViewModel()): LoginCoordinator = remember(viewModel) {
    LoginCoordinator(
        viewModel = viewModel,
    )
}
