package com.parksupark.soomjae.features.posts.meeting.presentation.write

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.parksupark.soomjae.features.posts.meeting.presentation.navigation.MeetingNavigator

@Composable
fun MeetingWriteRoute(
    navigator: MeetingNavigator,
    coordinator: MeetingWriteCoordinator = rememberMeetingWriteCoordinator(navigator),
) {
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle(MeetingWriteState())

    val actionsHandler: (MeetingWriteAction) -> Unit = { action ->
        coordinator.handle(action)
    }

    MeetingWriteScreen(
        state = uiState,
        onAction = actionsHandler,
    )
}
