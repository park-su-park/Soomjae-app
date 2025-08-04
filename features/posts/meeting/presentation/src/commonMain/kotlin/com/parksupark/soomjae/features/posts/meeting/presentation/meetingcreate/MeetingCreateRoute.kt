package com.parksupark.soomjae.features.posts.meeting.presentation.meetingcreate

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.parksupark.soomjae.features.posts.meeting.presentation.navigation.MeetingNavigator

@Composable
fun MeetingCreateRoute(
    navigator: MeetingNavigator,
    coordinator: MeetingCreateCoordinator = rememberMeetingCreateCoordinator(navigator),
) {
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle(MeetingCreateState())

    val actionsHandler: (MeetingCreateAction) -> Unit = { action ->
        coordinator.handle(action)
    }

    MeetingCreateScreen(
        state = uiState,
        onAction = actionsHandler,
    )
}
