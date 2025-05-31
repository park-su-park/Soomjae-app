package com.parksupark.soomjae.features.profile.presentation.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.parksupark.soomjae.features.profile.presentation.navigation.ProfileNavigator
import org.koin.compose.viewmodel.koinViewModel

internal class ProfileCoordinator(
    val navigator: ProfileNavigator,
    val viewModel: ProfileViewModel,
) {
    val screenStateFlow = viewModel.uiStateFlow

    fun handle(action: ProfileAction) {
        when (action) {
            ProfileAction.OnLoginClick -> navigator.navigateToLogin()

            ProfileAction.OnSettingClick -> {
                // TODO: navigate to setting
            }
        }
    }
}

@Composable
internal fun rememberProfileCoordinator(
    navigator: ProfileNavigator,
    viewModel: ProfileViewModel = koinViewModel(),
): ProfileCoordinator = remember(viewModel) {
    ProfileCoordinator(
        navigator = navigator,
        viewModel = viewModel,
    )
}
