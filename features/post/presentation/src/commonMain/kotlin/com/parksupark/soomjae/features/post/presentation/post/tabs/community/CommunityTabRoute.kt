package com.parksupark.soomjae.features.post.presentation.post.tabs.community

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import app.cash.paging.compose.collectAsLazyPagingItems
import com.parksupark.soomjae.features.post.presentation.post.PostAction
import kotlinx.coroutines.flow.map

@Composable
internal fun CommunityTabRoute(
    onPostAction: (PostAction) -> Unit,
    coordinator: CommunityTabCoordinator = rememberCommunityTabCoordinator(),
) {
    val uiState by coordinator.screenStateFlow.collectAsState(CommunityTabState())
    val posts = coordinator.screenStateFlow.map { it.posts }.collectAsLazyPagingItems()

    val actionsHandler: (CommunityTabAction) -> Unit = { action ->
        coordinator.handle(action)
    }

    CommunityTabScreen(
        state = uiState,
        onPostAction = onPostAction,
        onAction = actionsHandler,
        posts = posts,
    )
}
