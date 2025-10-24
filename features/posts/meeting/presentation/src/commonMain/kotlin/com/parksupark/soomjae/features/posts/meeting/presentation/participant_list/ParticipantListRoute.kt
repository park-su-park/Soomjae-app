package com.parksupark.soomjae.features.posts.meeting.presentation.participant_list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.parksupark.soomjae.features.posts.meeting.presentation.navigation.MeetingNavigator

@Composable
fun ParticipantListRoute(
    navigator: MeetingNavigator,
    coordinator: ParticipantListCoordinator =
        rememberParticipantListCoordinator(navigator = navigator),
) {
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle()

    val actionsHandler: (ParticipantListAction) -> Unit = { action ->
        coordinator.handle(action)
    }

    ParticipantListScreen(
        state = uiState,
        onAction = actionsHandler,
    )
}
