package com.parksupark.soomjae.features.post.presentation.communitywrite

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.parksupark.soomjae.features.post.presentation.navigation.PostNavigator

@Composable
fun CommunityWriteRoute(
    navigator: PostNavigator,
    coordinator: CommunityWriteCoordinator = rememberCommunityWriteCoordinator(navigator),
) {
    val uiState by coordinator.screenStateFlow.collectAsState(CommunityWriteState())

    val actionsHandler: (CommunityWriteAction) -> Unit = { action ->
        coordinator.handle(action)
    }

    CommunityWriteScreen(
        state = uiState,
        onAction = actionsHandler,
    )
}
