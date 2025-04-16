package com.parksupark.soomjae.features.home.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
internal fun HomeRoute(coordinator: HomeCoordinator = rememberHomeCoordinator()) {
    val uiState by coordinator.uiStateFlow.collectAsState(HomeState())

    val actionsHandler: (HomeAction) -> Unit = { action ->
        coordinator.handle(action)
    }

    HomeScreen(
        state = uiState,
        onAction = actionsHandler,
    )
}
