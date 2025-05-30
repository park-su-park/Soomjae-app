package com.parksupark.soomjae.features.post.presentation.post.tabs.community

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
internal fun CommunityTabRoute(coordinator: CommunityTabCoordinator = rememberCommunityTabCoordinator()) {
    val uiState by coordinator.screenStateFlow.collectAsState(CommunityTabState())

    val actionsHandler: (CommunityTabAction) -> Unit = { action ->
        coordinator.handle(action)
    }

    CommunityTabScreen(
        state = uiState,
        onAction = actionsHandler,
    )
}
