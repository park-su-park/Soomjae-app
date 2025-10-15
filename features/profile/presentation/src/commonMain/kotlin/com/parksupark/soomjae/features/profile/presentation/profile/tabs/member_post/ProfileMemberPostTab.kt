package com.parksupark.soomjae.features.profile.presentation.profile.tabs.member_post

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.cash.paging.compose.collectAsLazyPagingItems
import com.parksupark.soomjae.core.presentation.ui.ObserveAsEvents

@Composable
internal fun ProfileMemberPostTab(
    userId: Long,
    listState: LazyGridState,
    coordinator: ProfileMemberPostCoordinator = rememberProfileMemberPostCoordinator(userId),
) {
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle(ProfileMemberPostState())
    val posts = coordinator.posts.collectAsLazyPagingItems()

    val actionsHandler: (ProfileMemberPostAction) -> Unit = { action ->
        coordinator.handle(action)
    }

    ObserveAsEvents(
        flow = coordinator.events,
        onEvent = { event ->
            when (event) {
                ProfileMemberPostEvent.RefreshPosts -> posts.refresh()
            }
        },
    )

    ProfileMemberPostScreen(
        state = uiState,
        onAction = actionsHandler,
        listState = listState,
        posts = posts,
    )
}
