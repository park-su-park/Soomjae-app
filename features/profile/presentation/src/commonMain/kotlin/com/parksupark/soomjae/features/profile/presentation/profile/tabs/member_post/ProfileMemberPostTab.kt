package com.parksupark.soomjae.features.profile.presentation.profile.tabs.member_post

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
internal fun ProfileMemberPostTab(
    listState: LazyListState,
    coordinator: ProfileMemberPostCoordinator = rememberProfileMemberPostCoordinator(),
) {
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle(ProfileMemberPostState())

    val actionsHandler: (ProfileMemberPostAction) -> Unit = { action ->
        coordinator.handle(action)
    }

    ProfileMemberPostScreen(
        state = uiState,
        onAction = actionsHandler,
        listState = listState,
    )
}
