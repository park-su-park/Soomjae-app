package com.parksupark.soomjae.features.profile.presentation.profile.tabs.member_post

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.cash.paging.compose.collectAsLazyPagingItems

@Composable
internal fun ProfileMemberPostTab(
    userId: Long,
    listState: LazyListState,
    coordinator: ProfileMemberPostCoordinator = rememberProfileMemberPostCoordinator(userId),
) {
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle(ProfileMemberPostState())
    val posts = coordinator.posts.collectAsLazyPagingItems()

    val actionsHandler: (ProfileMemberPostAction) -> Unit = { action ->
        coordinator.handle(action)
    }

    ProfileMemberPostScreen(
        state = uiState,
        onAction = actionsHandler,
        listState = listState,
        posts = posts,
    )
}
