package com.parksupark.soomjae.features.auth.presentation.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.parksupark.soomjae.features.auth.presentation.navigation.AuthNavigator
import org.koin.compose.viewmodel.koinViewModel

class LoginCoordinator(
    val navigator: AuthNavigator,
    val viewModel: LoginViewModel,
) {
    val uiStateFlow = viewModel.uiState

    fun handle(action: LoginAction) {
        when (action) {
            LoginAction.OnCloseClick -> navigator.navigateBack()
            LoginAction.OnRegisterClick -> navigator.navigateToRegister()
            LoginAction.OnEmailLoginClick -> {}
        }
    }
}

@Composable
fun rememberLoginCoordinator(
    navigator: AuthNavigator,
    viewModel: LoginViewModel = koinViewModel(),
): LoginCoordinator = remember(viewModel) {
    LoginCoordinator(
        navigator = navigator,
        viewModel = viewModel,
    )
}
