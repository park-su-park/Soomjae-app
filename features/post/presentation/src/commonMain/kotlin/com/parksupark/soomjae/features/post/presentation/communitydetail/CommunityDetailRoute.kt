package com.parksupark.soomjae.features.post.presentation.communitydetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.parksupark.soomjae.features.post.presentation.navigation.PostNavigator

@Composable
internal fun CommunityDetailRoute(
    navigator: PostNavigator,
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
