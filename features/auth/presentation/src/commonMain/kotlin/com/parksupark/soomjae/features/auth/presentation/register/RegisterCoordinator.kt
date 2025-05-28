package com.parksupark.soomjae.features.auth.presentation.register

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.koin.compose.viewmodel.koinViewModel

class RegisterCoordinator(
    val onNavigateUp: () -> Unit,
    val viewModel: RegisterViewModel,
) {
    val screenStateFlow = viewModel.uiState

    fun handle(action: RegisterAction) {
        when (action) {
            RegisterAction.OnBackClick -> onNavigateUp()

            RegisterAction.OnRegisterClick -> {}

            RegisterAction.OnLoginClick -> {}
        }
    }
}

@Composable
fun rememberRegisterCoordinator(
    onNavigateUp: () -> Unit,
    viewModel: RegisterViewModel = koinViewModel(),
): RegisterCoordinator = remember(viewModel) {
    RegisterCoordinator(
        onNavigateUp = onNavigateUp,
        viewModel = viewModel,
    )
}
