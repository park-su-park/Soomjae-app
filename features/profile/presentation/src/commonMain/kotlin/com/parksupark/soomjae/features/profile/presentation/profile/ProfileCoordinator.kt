package com.parksupark.soomjae.features.profile.presentation.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.koin.compose.viewmodel.koinViewModel

class ProfileCoordinator(
    val viewModel: ProfileViewModel,
) {
    val screenStateFlow = viewModel.uiStateFlow

    fun handle(action: ProfileAction) {
        when (action) {
            ProfileAction.OnClick -> { // Handle action
            }
        }
    }
}

@Composable
fun rememberProfileCoordinator(viewModel: ProfileViewModel = koinViewModel()): ProfileCoordinator = remember(viewModel) {
    ProfileCoordinator(
        viewModel = viewModel,
    )
}
