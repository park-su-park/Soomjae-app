package com.parksupark.soomjae.features.posts.meeting.presentation.tab

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import app.cash.paging.compose.collectAsLazyPagingItems
import com.parksupark.soomjae.core.presentation.ui.ObserveAsEvents
import com.parksupark.soomjae.features.posts.common.presentation.PostAction

@Composable
fun MeetingTabRoute(
    onPostAction: (PostAction) -> Unit,
    coordinator: MeetingTabCoordinator = rememberMeetingTabCoordinator(onPostAction),
) {
    val uiState by coordinator.screenStateFlow.collectAsState(MeetingTabState())
    val posts = coordinator.posts.collectAsLazyPagingItems()

    val actionsHandler: (MeetingTabAction) -> Unit = { action ->
        coordinator.handle(action)
    }

    ObserveAsEvents(
        flow = coordinator.events,
    ) { event ->
        when (event) {
            MeetingTabEvent.NavigateToMeetingWrite -> onPostAction(
                PostAction.NavigateToMeetingWrite,
            )

            is MeetingTabEvent.RefreshPost -> {
                actionsHandler(MeetingTabAction.RefreshChange(true))
                posts.refresh()
            }
        }
    }

    MeetingTabScreen(
        state = uiState,
        onAction = actionsHandler,
        posts = posts,
    )
}
