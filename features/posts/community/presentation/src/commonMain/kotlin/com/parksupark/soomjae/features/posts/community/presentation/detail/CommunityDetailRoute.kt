package com.parksupark.soomjae.features.posts.community.presentation.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.parksupark.soomjae.features.posts.community.presentation.navigation.CommunityNavigator

@Composable
fun CommunityDetailRoute(
    navigator: CommunityNavigator,
    coordinator: CommunityDetailCoordinator = rememberCommunityDetailCoordinator(navigator),
) {
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle()

    val actionsHandler: (CommunityDetailAction) -> Unit = { action ->
        coordinator.handle(action)
    }

    CommunityDetailScreen(
        state = uiState,
        onAction = actionsHandler,
    )
}
