package com.parksupark.soomjae.features.profile.presentation.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.koin.compose.viewmodel.koinViewModel

class ProfileCoordinator(
    val onLoginClick: () -> Unit,
    val viewModel: ProfileViewModel,
) {
    val screenStateFlow = viewModel.uiStateFlow

    fun handle(action: ProfileAction) {
        when (action) {
            ProfileAction.OnLoginClick -> onLoginClick()

            ProfileAction.OnSettingClick -> {}
        }
    }
}

@Composable
fun rememberProfileCoordinator(
    onLoginClick: () -> Unit,
    viewModel: ProfileViewModel = koinViewModel(),
): ProfileCoordinator = remember(viewModel) {
    ProfileCoordinator(
        onLoginClick = onLoginClick,
        viewModel = viewModel,
    )
}
