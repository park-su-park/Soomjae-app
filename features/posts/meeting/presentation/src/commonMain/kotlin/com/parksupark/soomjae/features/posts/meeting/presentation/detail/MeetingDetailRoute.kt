package com.parksupark.soomjae.features.posts.meeting.presentation.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.parksupark.soomjae.core.presentation.ui.ObserveAsEvents
import com.parksupark.soomjae.features.posts.meeting.presentation.navigation.MeetingNavigator

@Composable
fun MeetingDetailRoute(
    navigator: MeetingNavigator,
    coordinator: MeetingDetailCoordinator = rememberMeetingDetailCoordinator(navigator),
) {
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle()

    val actionsHandler: (MeetingDetailAction) -> Unit = { action ->
        coordinator.handle(action)
    }

    ObserveAsEvents(
        flow = coordinator.events,
    ) { event ->
        when (event) {
            is MeetingDetailEvent.NavigateToEditPost -> coordinator.navigateToEditPost(event.postId)

            MeetingDetailEvent.PostDeleted -> coordinator.navigateBack()
        }
    }

    MeetingDetailScreen(
        state = uiState,
        onAction = actionsHandler,
    )
}
