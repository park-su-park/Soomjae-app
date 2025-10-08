package com.parksupark.soomjae.features.posts.member.presentation.post_list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.cash.paging.compose.collectAsLazyPagingItems
import com.parksupark.soomjae.core.presentation.ui.ObserveAsEvents
import com.parksupark.soomjae.features.posts.common.presentation.PostAction
import kotlinx.coroutines.flow.map

@Composable
fun MemberPostListRoute(
    onPostAction: (PostAction) -> Unit,
    coordinator: MemberPostListCoordinator = rememberMemberPostListCoordinator(),
) {
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle()
    val posts = coordinator.screenStateFlow.map { it.posts }.collectAsLazyPagingItems()

    val actionHandler: (MemberPostListAction) -> Unit = { action ->
        coordinator.handle(action)
    }

    ObserveAsEvents(
        flow = coordinator.events,
        onEvent = {
            when (it) {
                is MemberPostListEvent.NavigateToWritePost -> onPostAction(PostAction.OnNavigateToMemberWrite)
                is MemberPostListEvent.RefreshPosts -> {
                    actionHandler(MemberPostListAction.OnRefreshChange(true))
                    posts.refresh()
                }
            }
        },
    )

    MemberPostListTab(
        state = uiState,
        onAction = actionHandler,
        onPostAction = onPostAction,
        posts = posts,
    )
}
