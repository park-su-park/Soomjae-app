package com.parksupark.soomjae.features.profile.presentation.profile_edit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.parksupark.soomjae.features.profile.presentation.navigation.ProfileNavigator
import org.koin.compose.viewmodel.koinViewModel

class ProfileEditCoordinator(
    val navigator: ProfileNavigator,
    val viewModel: ProfileEditViewModel,
) {
    val screenStateFlow = viewModel.stateFlow
    val eventFlow = viewModel.events

    fun handle(action: ProfileEditAction) {
        when (action) {
            ProfileEditAction.OnBackClick -> navigateBack()

            is ProfileEditAction.OnProfileImageSelect -> {
                viewModel.uploadProfileImage(action.image)
            }

            ProfileEditAction.OnSubmitClick -> viewModel.submitProfile()
        }
    }

    fun navigateBack() {
        navigator.navigateBack()
    }
}

@Composable
fun rememberProfileEditCoordinator(
    navigator: ProfileNavigator,
    viewModel: ProfileEditViewModel = koinViewModel(),
): ProfileEditCoordinator = remember(navigator, viewModel) {
    ProfileEditCoordinator(
        navigator = navigator,
        viewModel = viewModel,
    )
}
