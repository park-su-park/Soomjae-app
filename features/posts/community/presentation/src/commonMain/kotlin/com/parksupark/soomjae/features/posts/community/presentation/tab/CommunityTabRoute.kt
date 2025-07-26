package com.parksupark.soomjae.features.posts.community.presentation.tab

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import app.cash.paging.compose.collectAsLazyPagingItems
import com.parksupark.soomjae.core.presentation.ui.ObserveAsEvents
import com.parksupark.soomjae.features.posts.common.presentation.PostAction
import kotlinx.coroutines.flow.map

@Composable
fun CommunityTabRoute(
    onPostAction: (PostAction) -> Unit,
    coordinator: CommunityTabCoordinator = rememberCommunityTabCoordinator(onPostAction = onPostAction),
) {
    val uiState by coordinator.screenStateFlow.collectAsState(CommunityTabState())
    val posts = coordinator.screenStateFlow.map { it.posts }.collectAsLazyPagingItems()

    val actionsHandler: (CommunityTabAction) -> Unit = { action ->
        coordinator.handle(action)
    }

    ObserveAsEvents(
        flow = coordinator.eventFlow,
    ) { event ->
        when (event) {
            CommunityTabEvent.RefreshPosts -> {
                actionsHandler(CommunityTabAction.OnRefreshChange(true))
                posts.refresh()
            }

            CommunityTabEvent.NavigateToCommunityWrite -> onPostAction(PostAction.NavigateToCommunityWrite)
        }
    }

    CommunityTabScreen(
        state = uiState,
        onAction = actionsHandler,
        posts = posts,
    )
}
