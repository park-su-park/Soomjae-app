package com.parksupark.soomjae.features.posts.common.presentation.tab

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import app.cash.paging.compose.collectAsLazyPagingItems

@Composable
fun MeetingTabRoute(coordinator: MeetingTabCoordinator = rememberMeetingTabCoordinator()) {
    val uiState by coordinator.screenStateFlow.collectAsState(MeetingTabState())
    val posts = coordinator.posts.collectAsLazyPagingItems()

    val actionsHandler: (MeetingTabAction) -> Unit = { action ->
        coordinator.handle(action)
    }

    MeetingTabScreen(
        state = uiState,
        onAction = actionsHandler,
        posts = posts,
    )
}
