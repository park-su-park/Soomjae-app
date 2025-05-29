package com.parksupark.soomjae.features.post.presentation.post

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun PostRoute(coordinator: PostCoordinator = rememberPostCoordinator()) {
    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsState(PostState())

    // UI Actions
    val actionsHandler: (PostAction) -> Unit = { action ->
        coordinator.handle(action)
    }

    // UI Rendering
    PostScreen(
        state = uiState,
        onAction = actionsHandler,
    )
}
