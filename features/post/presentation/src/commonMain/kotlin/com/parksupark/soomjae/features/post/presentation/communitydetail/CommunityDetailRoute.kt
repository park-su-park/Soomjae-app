package com.parksupark.soomjae.features.post.presentation.communitydetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun CommunityDetailRoute(coordinator: CommunityDetailCoordinator = rememberCommunityDetailCoordinator()) {
    val uiState by coordinator.screenStateFlow.collectAsState(CommunityDetailState())

    val actionsHandler: (CommunityDetailAction) -> Unit = { action ->
        coordinator.handle(action)
    }

    CommunityDetailScreen(
        state = uiState,
        onAction = actionsHandler,
    )
}
