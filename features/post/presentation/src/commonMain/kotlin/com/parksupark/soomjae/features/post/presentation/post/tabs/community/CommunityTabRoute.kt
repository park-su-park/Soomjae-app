package com.parksupark.soomjae.features.post.presentation.post.tabs.community

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import app.cash.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.flow.map

@Composable
internal fun CommunityTabRoute(coordinator: CommunityTabCoordinator = rememberCommunityTabCoordinator()) {
    val uiState by coordinator.screenStateFlow.collectAsState(CommunityTabState())
    val posts = coordinator.screenStateFlow.map { it.posts }.collectAsLazyPagingItems()

    val actionsHandler: (CommunityTabAction) -> Unit = { action ->
        coordinator.handle(action)
    }

    CommunityTabScreen(
        state = uiState,
        onAction = actionsHandler,
        posts = posts,
    )
}
