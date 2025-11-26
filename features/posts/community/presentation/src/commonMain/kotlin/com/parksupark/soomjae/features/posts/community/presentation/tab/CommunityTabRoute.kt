package com.parksupark.soomjae.features.posts.community.presentation.tab

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.parksupark.soomjae.core.presentation.ui.ObserveAsEvents
import com.parksupark.soomjae.features.posts.common.presentation.PostAction
import com.parksupark.soomjae.features.posts.community.presentation.tab.post.CommunityTabPostEvent

@Composable
fun CommunityTabRoute(
    onPostAction: (PostAction) -> Unit,
    coordinator: CommunityTabCoordinator =
        rememberCommunityTabCoordinator(onPostAction = onPostAction),
) {
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle(CommunityTabState())
    val posts = coordinator.posts.collectAsLazyPagingItems()

    val actionsHandler: (CommunityTabAction) -> Unit = { action ->
        coordinator.handle(action)
    }

    ObserveAsEvents(
        flow = coordinator.eventFlow,
    ) { event ->
        when (event) {
            CommunityTabPostEvent.RefreshPosts -> {
                actionsHandler(CommunityTabAction.RefreshChange(true))
                posts.refresh()
            }

            CommunityTabPostEvent.NavigateToCommunityWrite -> onPostAction(
                PostAction.NavigateToCommunityWrite,
            )
        }
    }

    CommunityTabScreen(
        state = uiState,
        onAction = actionsHandler,
        posts = posts,
    )
}
