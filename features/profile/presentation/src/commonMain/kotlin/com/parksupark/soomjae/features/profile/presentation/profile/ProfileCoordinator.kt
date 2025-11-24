package com.parksupark.soomjae.features.profile.presentation.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.parksupark.soomjae.features.profile.presentation.navigation.ProfileNavigator
import org.koin.compose.viewmodel.koinViewModel

internal class ProfileCoordinator(
    val navigator: ProfileNavigator,
    val viewModel: ProfileViewModel,
) {
    val screenStateFlow = viewModel.state

    fun handle(action: ProfileAction) {
        when (action) {
            ProfileAction.OnLoginClick -> navigator.navigateToLogin()

            ProfileAction.OnLogoutClick -> viewModel.logout()

            ProfileAction.OnSettingClick -> navigator.navigateToSetting()

            is ProfileAction.OnEditProfileClick -> navigator.navigateToProfileEdit(action.memberId)

            is ProfileAction.OnEditIntroductionClick -> navigator.navigateToIntroductionEdit(
                action.memberId,
            )
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
