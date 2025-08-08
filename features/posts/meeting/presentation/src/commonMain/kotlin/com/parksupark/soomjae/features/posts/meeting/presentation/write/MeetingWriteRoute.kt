package com.parksupark.soomjae.features.posts.meeting.presentation.write

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.parksupark.soomjae.core.presentation.ui.ObserveAsEvents
import com.parksupark.soomjae.core.presentation.ui.components.SoomjaeSnackbarHost
import com.parksupark.soomjae.core.presentation.ui.components.showSnackbar
import com.parksupark.soomjae.features.posts.meeting.presentation.navigation.MeetingNavigator
import kotlinx.coroutines.launch

@Composable
fun MeetingWriteRoute(
    navigator: MeetingNavigator,
    coordinator: MeetingWriteCoordinator = rememberMeetingWriteCoordinator(navigator),
) {
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle(MeetingWriteState())

    val actionsHandler: (MeetingWriteAction) -> Unit = { action ->
        coordinator.handle(action)
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    ObserveAsEvents(
        flow = coordinator.events,
    ) { event ->
        when (event) {
            is MeetingWriteEvent.OnPostCreateSuccess -> navigator.navigateToMeetingDetail(event.postId)

            is MeetingWriteEvent.OnPostCreateFailure -> coroutineScope.launch {
                snackbarHostState.showSnackbar(event.error)
            }
        }
    }

    MeetingWriteScreen(
        state = uiState,
        onAction = actionsHandler,
        snackbarHost = { SoomjaeSnackbarHost(snackbarHostState) },
    )
}
