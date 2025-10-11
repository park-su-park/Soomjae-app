package com.parksupark.soomjae.features.posts.member.presentation.post_list

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.cash.paging.compose.collectAsLazyPagingItems
import com.parksupark.soomjae.core.presentation.ui.ObserveAsEvents
import com.parksupark.soomjae.features.posts.common.presentation.PostAction
import com.parksupark.soomjae.features.posts.member.presentation.post_list.components.CommentBottomSheetScreen
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemberPostListRoute(
    onPostAction: (PostAction) -> Unit,
    coordinator: MemberPostListCoordinator = rememberMemberPostListCoordinator(),
) {
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle()
    val commentState by coordinator.commentStateFlow.collectAsStateWithLifecycle()
    val posts = coordinator.screenStateFlow.map { it.posts }.collectAsLazyPagingItems()
    val showSheet = uiState.selectedPostId != null

    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

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

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                coroutineScope.launch {
                    sheetState.hide()
                    actionHandler(MemberPostListAction.OnBottomSheetDismiss)
                }
            },
        ) {
            CommentBottomSheetScreen(
                state = commentState,
            )
        }
    }
}
