package com.parksupark.soomjae.features.posts.meeting.presentation.tab

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.cash.paging.compose.collectAsLazyPagingItems
import com.parksupark.soomjae.core.presentation.ui.ObserveAsEvents
import com.parksupark.soomjae.core.presentation.ui.components.showSnackbar
import com.parksupark.soomjae.features.posts.common.presentation.PostAction
import com.parksupark.soomjae.features.posts.meeting.presentation.tab.MeetingTabAction.RefreshChange
import com.parksupark.soomjae.features.posts.meeting.presentation.tab.filter.MeetingTabFilterEvent
import com.parksupark.soomjae.features.posts.meeting.presentation.tab.post.MeetingTabPostEvent
import kotlinx.coroutines.launch

@Composable
fun MeetingTabRoute(
    onPostAction: (PostAction) -> Unit,
    coordinator: MeetingTabCoordinator = rememberMeetingTabCoordinator(onPostAction),
) {
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle()
    val posts = coordinator.posts.collectAsLazyPagingItems()
    val snackbarHostState = remember { SnackbarHostState() }

    val actionsHandler: (MeetingTabAction) -> Unit = { action ->
        coordinator.handle(action)
    }

    val scope = rememberCoroutineScope()
    ObserveAsEvents(
        flow = coordinator.events,
    ) { event ->
        when (event) {
            is MeetingTabEvent.FromFilter -> when (val filterEvent = event.event) {
                is MeetingTabFilterEvent.OnError -> scope.launch {
                    snackbarHostState.showSnackbar(uiText = filterEvent.message, isError = true)
                }
            }

            is MeetingTabEvent.FromPost -> when (event.event) {
                MeetingTabPostEvent.NavigateToMeetingWrite -> onPostAction(
                    PostAction.NavigateToMeetingWrite,
                )

                MeetingTabPostEvent.RefreshPost -> {
                    actionsHandler(RefreshChange(true))
                    posts.refresh()
                }
            }
        }
    }

    MeetingTabScreen(
        state = uiState,
        snackbarHostState = snackbarHostState,
        onAction = actionsHandler,
        posts = posts,
    )
}
