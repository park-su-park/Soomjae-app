package com.parksupark.soomjae.features.posts.member.presentation.post_list

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalFocusManager
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.cash.paging.compose.collectAsLazyPagingItems
import com.composables.core.SheetDetent
import com.composables.core.rememberModalBottomSheetState
import com.parksupark.soomjae.core.presentation.ui.ObserveAsEvents
import com.parksupark.soomjae.core.presentation.ui.components.showSnackbar
import com.parksupark.soomjae.features.posts.common.presentation.PostAction
import com.parksupark.soomjae.features.posts.member.presentation.post_list.comment.MemberPostCommentEvent
import com.parksupark.soomjae.features.posts.member.presentation.post_list.components.CommentBottomSheet
import kotlinx.coroutines.launch

@Composable
fun MemberPostListRoute(
    onPostAction: (PostAction) -> Unit,
    coordinator: MemberPostListCoordinator = rememberMemberPostListCoordinator(),
) {
    val uiState by coordinator.postStateFlow.collectAsStateWithLifecycle()
    val commentState by coordinator.commentStateFlow.collectAsStateWithLifecycle()
    val posts = coordinator.posts.collectAsLazyPagingItems()

    val snackbarHostState = remember { SnackbarHostState() }

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

    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    ObserveAsEvents(
        flow = coordinator.events,
        onEvent = { event ->
            when (event) {
                is MemberPostListEvent.NavigateToWritePost -> onPostAction(
                    PostAction.NavigateToMemberWrite,
                )

                is MemberPostListEvent.RefreshPosts -> {
                    actionHandler(MemberPostListAction.OnRefreshChange(true))
                    posts.refresh()
                }

                is MemberPostListEvent.FromComment -> when (val event = event.commentEvent) {
                    is MemberPostCommentEvent.CommentSubmissionSuccess -> focusManager.clearFocus()

                    is MemberPostCommentEvent.Error -> coroutineScope.launch {
                        snackbarHostState.showSnackbar(event.message, isError = true)
                    }
                }
            }
        },
    )

    MemberPostListTab(
        state = uiState,
        onAction = actionHandler,
        snackbarHostState = snackbarHostState,
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
