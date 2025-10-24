package com.parksupark.soomjae.features.posts.member.presentation.post_list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.parksupark.soomjae.features.posts.member.presentation.post_list.comment.MemberPostCommentViewModel
import org.koin.compose.viewmodel.koinViewModel

class MemberPostListCoordinator(
    val postViewModel: MemberPostListViewModel,
    private val commentViewModel: MemberPostCommentViewModel,
) {
    val screenStateFlow = postViewModel.stateFlow
    val commentStateFlow = commentViewModel.stateFlow
    val events = postViewModel.events

    fun handle(action: MemberPostListAction) {
        when (action) {
            is MemberPostListAction.OnPullToRefresh -> postViewModel.refreshPosts()
            is MemberPostListAction.OnWritePostClick -> postViewModel.handleWritePostClick()
            is MemberPostListAction.OnRefreshChange -> postViewModel.setRefreshing(
                action.isRefreshing,
            )
            is MemberPostListAction.OnCommentClick -> handleCommentClick(action.postId)
            is MemberPostListAction.OnBottomSheetDismiss -> handleBottomSheetDismiss()
        }
    }

    private fun handleCommentClick(postId: Long) {
        postViewModel.setSelectedPostId(postId)
        commentViewModel.loadCommentsForPost(postId)
    }

    private fun handleBottomSheetDismiss() {
        postViewModel.setSelectedPostId(null)
        commentViewModel.clearComments()
    }
}

@Composable
fun rememberMemberPostListCoordinator(
    postViewModel: MemberPostListViewModel = koinViewModel(),
    commentViewModel: MemberPostCommentViewModel = koinViewModel(),
): MemberPostListCoordinator = remember(postViewModel, commentViewModel) {
    MemberPostListCoordinator(
        postViewModel = postViewModel,
        commentViewModel = commentViewModel,
    )
}
