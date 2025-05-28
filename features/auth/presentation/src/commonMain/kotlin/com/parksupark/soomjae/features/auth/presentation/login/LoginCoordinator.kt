package com.parksupark.soomjae.features.auth.presentation.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.koin.compose.viewmodel.koinViewModel

class LoginCoordinator(
    val onRegisterClick: () -> Unit,
    val viewModel: LoginViewModel,
) {
    val uiStateFlow = viewModel.uiState

    fun handle(action: LoginAction) {
        when (action) {
            LoginAction.OnClick -> {}
            LoginAction.OnRegisterClick -> onRegisterClick()
            LoginAction.OnEmailLoginClick -> {}
        }
    }
}

@Composable
fun rememberLoginCoordinator(
    onRegisterClick: () -> Unit,
    viewModel: LoginViewModel = koinViewModel(),
): LoginCoordinator = remember(viewModel) {
    LoginCoordinator(
        onRegisterClick = onRegisterClick,
        viewModel = viewModel,
    )
}
