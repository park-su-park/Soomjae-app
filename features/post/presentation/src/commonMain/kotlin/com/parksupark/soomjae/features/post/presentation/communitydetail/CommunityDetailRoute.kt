package com.parksupark.soomjae.features.post.presentation.communitydetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
internal fun CommunityDetailRoute(coordinator: CommunityDetailCoordinator = rememberCommunityDetailCoordinator()) {
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle()

    val actionsHandler: (CommunityDetailAction) -> Unit = { action ->
        coordinator.handle(action)
    }

    CommunityDetailScreen(
        state = uiState,
        onAction = actionsHandler,
    )
}
