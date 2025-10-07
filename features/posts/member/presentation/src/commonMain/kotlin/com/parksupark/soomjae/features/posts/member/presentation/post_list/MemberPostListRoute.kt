package com.parksupark.soomjae.features.posts.member.presentation.post_list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.parksupark.soomjae.core.presentation.ui.ObserveAsEvents
import com.parksupark.soomjae.features.posts.common.presentation.PostAction

@Composable
fun MemberPostListRoute(
    onPostAction: (PostAction) -> Unit,
    coordinator: MemberPostListCoordinator = rememberMemberPostListCoordinator(),
) {
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle()

    val actionHandler: (MemberPostListAction) -> Unit = { action ->
        coordinator.handle(action)
    }

    ObserveAsEvents(
        flow = coordinator.events,
        onEvent = {
            when (it) {
                is MemberPostListEvent.NavigateToWritePost -> onPostAction(PostAction.OnNavigateToMemberWrite)
            }
        },
    )

    MemberPostListTab(
        state = uiState,
        onAction = actionHandler,
        onPostAction = onPostAction,
    )
}
