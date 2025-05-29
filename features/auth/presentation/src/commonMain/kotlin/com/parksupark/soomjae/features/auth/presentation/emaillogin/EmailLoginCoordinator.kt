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

    fun handle(action: EmailLoginAction) {
        when (action) {
            EmailLoginAction.OnClick -> { // Handle action
            }
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
