package com.parksupark.soomjae.features.profile.presentation.profile.tabs.meeting_post

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.parksupark.soomjae.features.profile.presentation.profile.ProfileAction

@Composable
internal fun ProfileMeetingPostTab(
    userId: Long,
    onPostAction: (ProfileAction) -> Unit,
    coordinator: MeetingPostCoordinator = rememberMeetingPostCoordinator(
        memberId = userId,
        onProfileAction = onPostAction,
    ),
    listState: LazyListState,
) {
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle(MeetingPostState())

    val actionsHandler: (MeetingPostAction) -> Unit = { action ->
        coordinator.handle(action)
    }

    ProfileMeetingPostScreen(
        state = uiState,
        onAction = actionsHandler,
        listState = listState,
    )
}
