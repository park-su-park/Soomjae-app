package com.parksupark.soomjae.features.posts.member.presentation.post_list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.cash.paging.compose.collectAsLazyPagingItems
import com.composables.core.SheetDetent
import com.composables.core.rememberModalBottomSheetState
import com.parksupark.soomjae.core.presentation.ui.ObserveAsEvents
import com.parksupark.soomjae.features.posts.common.presentation.PostAction
import com.parksupark.soomjae.features.posts.member.presentation.post_list.components.CommentBottomSheet

@Composable
fun MemberPostListRoute(
    onPostAction: (PostAction) -> Unit,
    coordinator: MemberPostListCoordinator = rememberMemberPostListCoordinator(),
) {
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle()
    val commentState by coordinator.commentStateFlow.collectAsStateWithLifecycle()
    val posts = coordinator.posts.collectAsLazyPagingItems()

    val sheetState = rememberModalBottomSheetState(
        initialDetent = SheetDetent.Hidden,
    )

    LaunchedEffect(uiState.selectedPostId) {
        val showComments = uiState.selectedPostId != null
        if (showComments) {
            sheetState.animateTo(SheetDetent.FullyExpanded)
        }
    }

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
        posts = posts,
    )

    CommentBottomSheet(
        state = sheetState,
        onAction = actionHandler,
        onDismiss = {
            actionHandler(MemberPostListAction.OnBottomSheetDismiss)
        },
        commentState = commentState,
    )
}
