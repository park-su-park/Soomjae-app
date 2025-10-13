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
import com.parksupark.soomjae.features.posts.meeting.presentation.write.creation.MeetingCreationAction
import com.parksupark.soomjae.features.posts.meeting.presentation.write.creation.MeetingCreationScreen
import com.parksupark.soomjae.features.posts.meeting.presentation.write.post_content.MeetingPostContentScreen
import com.parksupark.soomjae.features.posts.meeting.presentation.write.step.MeetingPostWriteStep
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun MeetingWriteRoute(
    navigator: MeetingNavigator,
    coordinator: MeetingPostWriteCoordinator = koinViewModel { parametersOf(navigator) },
) {
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle()

    val writeActionsHandler: (MeetingPostWriteAction) -> Unit = { action ->
        coordinator.handle(action)
    }

    val createActionHandler: (MeetingCreationAction) -> Unit = { action ->
        coordinator.handle(action)
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    ObserveAsEvents(
        flow = coordinator.events,
    ) { event ->
        when (event) {
            is MeetingPostWriteEvent.OnPostCreateSuccess -> navigator.navigateToMeetingDetail(event.postId)

            is MeetingPostWriteEvent.OnPostCreateFailure -> coroutineScope.launch {
                snackbarHostState.showSnackbar(event.error)
            }
        }
    }

    val stepState = uiState.stepState
    when (stepState.step) {
        MeetingPostWriteStep.CONTENT -> MeetingPostContentScreen(
            state = uiState.contentState,
            onAction = writeActionsHandler,
            snackbarHost = { SoomjaeSnackbarHost(snackbarHostState) },
        )

        MeetingPostWriteStep.CREATE -> MeetingCreationScreen(
            state = uiState.creationState,
            onAction = createActionHandler,
        )
    }
}
