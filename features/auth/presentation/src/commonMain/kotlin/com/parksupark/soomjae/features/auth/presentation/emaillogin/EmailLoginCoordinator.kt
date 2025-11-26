package com.parksupark.soomjae.features.auth.presentation.emaillogin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.parksupark.soomjae.features.auth.presentation.navigation.AuthNavigator
import org.koin.compose.viewmodel.koinViewModel

class EmailLoginCoordinator(
    val navigator: AuthNavigator,
    val viewModel: EmailLoginViewModel,
) {
    val screenStateFlow = viewModel.uiStateFlow
    val eventsFlow = viewModel.eventChannel

    fun handle(action: EmailLoginAction) {
        when (action) {
            EmailLoginAction.OnBackClick -> navigator.navigateBack()

            EmailLoginAction.OnLoginClick -> viewModel.login()

            EmailLoginAction.OnSaveEmailClick -> viewModel.toggleSaveEmail()
        }
    }
}

@Composable
fun rememberEmailLoginCoordinator(
    navigator: AuthNavigator,
    viewModel: EmailLoginViewModel = koinViewModel(),
): EmailLoginCoordinator = remember(viewModel) {
    EmailLoginCoordinator(
        navigator = navigator,
        viewModel = viewModel,
    )
}
