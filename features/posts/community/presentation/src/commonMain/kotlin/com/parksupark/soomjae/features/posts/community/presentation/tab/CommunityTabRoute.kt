package com.parksupark.soomjae.features.posts.community.presentation.tab

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import app.cash.paging.compose.collectAsLazyPagingItems
import com.parksupark.soomjae.core.presentation.ui.ObserveAsEvents
import com.parksupark.soomjae.features.posts.common.presentation.PostAction
import com.parksupark.soomjae.features.posts.community.presentation.tab.post.CommunityTabPostAction
import com.parksupark.soomjae.features.posts.community.presentation.tab.post.CommunityTabPostEvent
import com.parksupark.soomjae.features.posts.community.presentation.tab.post.CommunityTabPostState

@Composable
fun CommunityTabRoute(
    onPostAction: (PostAction) -> Unit,
    coordinator: CommunityTabCoordinator =
        rememberCommunityTabCoordinator(onPostAction = onPostAction),
) {
    val uiState by coordinator.screenStateFlow.collectAsState(CommunityTabPostState())
    val posts = coordinator.posts.collectAsLazyPagingItems()

    val actionsHandler: (CommunityTabPostAction) -> Unit = { action ->
        coordinator.handle(action)
    }

    ObserveAsEvents(
        flow = coordinator.eventFlow,
    ) { event ->
        when (event) {
            CommunityTabPostEvent.RefreshPosts -> {
                actionsHandler(CommunityTabPostAction.OnRefreshChange(true))
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
