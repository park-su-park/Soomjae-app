package com.parksupark.soomjae.features.profile.presentation.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun ProfileRoute(coordinator: ProfileCoordinator = rememberProfileCoordinator()) {
    val uiState by coordinator.screenStateFlow.collectAsState(ProfileState())

    val actionsHandler: (ProfileAction) -> Unit = { action ->
        coordinator.handle(action)
    }

    ProfileScreen(
        state = uiState,
        onAction = actionsHandler,
    )
}
