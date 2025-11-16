package com.parksupark.soomjae.features.posts.member.presentation.post_list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.cash.paging.compose.collectAsLazyPagingItems
import com.composables.core.SheetDetent
import com.composables.core.rememberModalBottomSheetState
import com.parksupark.soomjae.core.presentation.ui.ObserveAsEvents
import com.parksupark.soomjae.features.posts.common.presentation.PostAction
import com.parksupark.soomjae.features.posts.member.presentation.post_list.components.CommentBottomSheet
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@Composable
fun MemberPostListRoute(
    onPostAction: (PostAction) -> Unit,
    coordinator: MemberPostListCoordinator = rememberMemberPostListCoordinator(),
) {
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle()
    val commentState by coordinator.commentStateFlow.collectAsStateWithLifecycle()
    val posts = coordinator.screenStateFlow.map { it.posts }.collectAsLazyPagingItems()
    val showSheet = remember(uiState.selectedPostId) {
        uiState.selectedPostId != null
    }

    val coroutineScope = rememberCoroutineScope()
    val actionHandler: (MemberPostListAction) -> Unit = { action ->
        coordinator.handle(action)
    }

    ObserveAsEvents(
        flow = coordinator.events,
        onEvent = {
            when (it) {
                is MemberPostListEvent.NavigateToWritePost -> onPostAction(
                    PostAction.NavigateToMemberWrite,
                )

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

    if (showSheet) {
        val state = rememberModalBottomSheetState(
            initialDetent = SheetDetent.FullyExpanded,
        )

        CommentBottomSheet(
            state = state,
            onDismiss = {
                coroutineScope.launch {
                    state.animateTo(SheetDetent.Hidden)
                    actionHandler(MemberPostListAction.OnBottomSheetDismiss)
                }
            },
            commentState = commentState,
        )
    }
}
