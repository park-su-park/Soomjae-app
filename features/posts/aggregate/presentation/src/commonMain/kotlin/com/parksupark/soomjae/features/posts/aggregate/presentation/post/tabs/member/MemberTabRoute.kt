package com.parksupark.soomjae.features.posts.aggregate.presentation.post.tabs.member

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun MemberTabRoute(coordinator: MemberTabCoordinator = rememberMemberTabCoordinator()) {
    val uiState by coordinator.screenStateFlow.collectAsState(MemberTabState())

    val actionsHandler: (MemberTabAction) -> Unit = { action ->
        coordinator.handle(action)
    }

    MemberTabScreen(
        state = uiState,
        onAction = actionsHandler,
    )
}
