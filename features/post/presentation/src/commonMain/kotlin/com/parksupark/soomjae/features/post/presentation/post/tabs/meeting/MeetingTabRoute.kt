package com.parksupark.soomjae.features.post.presentation.post.tabs.meeting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun MeetingTabRoute(coordinator: MeetingTabCoordinator = rememberMeetingTabCoordinator()) {
    val uiState by coordinator.screenStateFlow.collectAsState(MeetingTabState())

    val actionsHandler: (MeetingTabAction) -> Unit = { action ->
        coordinator.handle(action)
    }

    MeetingTabScreen(
        state = uiState,
        onAction = actionsHandler,
    )
}
