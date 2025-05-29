package com.parksupark.soomjae.features.post.presentation.post

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun PostRoute(
    bottomBar: @Composable () -> Unit,
    coordinator: PostCoordinator = rememberPostCoordinator(),
) {
    val uiState by coordinator.screenStateFlow.collectAsState(PostState())

    val actionsHandler: (PostAction) -> Unit = { action ->
        coordinator.handle(action)
    }

    PostScreen(
        state = uiState,
        onAction = actionsHandler,
        bottomBar = bottomBar,
    )
}
