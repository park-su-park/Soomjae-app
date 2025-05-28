package com.parksupark.soomjae.features.profile.presentation.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ProfileRoute(
    bottomBar: @Composable () -> Unit,
    onLoginClick: () -> Unit,
    coordinator: ProfileCoordinator = rememberProfileCoordinator(onLoginClick),
) {
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle()

    val actionsHandler: (ProfileAction) -> Unit = { action ->
        coordinator.handle(action)
    }

    when (val profileState = uiState) {
        is ProfileState.MyProfileState -> MyProfileScreen(bottomBar, profileState, actionsHandler)
        is ProfileState.OtherProfileState -> OthersProfileScreen(profileState, actionsHandler)
    }
}
