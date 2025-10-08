package com.parksupark.soomjae.features.posts.member.presentation.post_list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.koin.compose.viewmodel.koinViewModel

class MemberPostListCoordinator(
    val viewModel: MemberPostListViewModel,
) {
    val screenStateFlow = viewModel.stateFlow
    val events = viewModel.events

    fun handle(action: MemberPostListAction) {
        when (action) {
            is MemberPostListAction.OnPullToRefresh -> viewModel.refreshPosts()
            is MemberPostListAction.OnWritePostClick -> viewModel.handleWritePostClick()
            is MemberPostListAction.OnRefreshChange -> viewModel.setRefreshing(action.isRefreshing)
        }
    }
}

@Composable
fun rememberMemberPostListCoordinator(viewModel: MemberPostListViewModel = koinViewModel()): MemberPostListCoordinator =
    remember(viewModel) {
        MemberPostListCoordinator(
            viewModel = viewModel,
        )
    }
