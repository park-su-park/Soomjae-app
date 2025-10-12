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
import com.parksupark.soomjae.features.posts.meeting.presentation.write.compose.MeetingComposeScreen
import com.parksupark.soomjae.features.posts.meeting.presentation.write.create.MeetingCreateAction
import com.parksupark.soomjae.features.posts.meeting.presentation.write.create.MeetingCreateScreen
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun MeetingWriteRoute(
    navigator: MeetingNavigator,
    coordinator: MeetingWriteCoordinator = koinViewModel { parametersOf(navigator) },
) {
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle()

    val writeActionsHandler: (MeetingWriteAction) -> Unit = { action ->
        coordinator.handle(action)
    }

    val createActionHandler: (MeetingCreateAction) -> Unit = { action ->
        // TODO: Handle create actions if needed
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

    val screenState = uiState.screenState
    when (screenState.screenState) {
        MeetingPostWriteScreenState.COMPOSE -> MeetingComposeScreen(
            state = uiState.composeState,
            onAction = writeActionsHandler,
            snackbarHost = { SoomjaeSnackbarHost(snackbarHostState) },
        )

        MeetingPostWriteScreenState.CREATE -> MeetingCreateScreen(
            state = uiState.createState,
            onAction = createActionHandler,
        )
    }
}
