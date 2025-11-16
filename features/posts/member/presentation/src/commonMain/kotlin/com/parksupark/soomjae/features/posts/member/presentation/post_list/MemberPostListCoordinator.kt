package com.parksupark.soomjae.features.posts.member.presentation.post_list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import com.parksupark.soomjae.features.posts.member.presentation.post_list.comment.MemberPostCommentViewModel
import org.koin.compose.viewmodel.koinViewModel

@Stable
class MemberPostListCoordinator(
    val postViewModel: MemberPostListViewModel,
    private val commentViewModel: MemberPostCommentViewModel,
) {
    val screenStateFlow = postViewModel.stateFlow
    val commentStateFlow = commentViewModel.stateFlow

    val posts = postViewModel.posts

    val events = postViewModel.events

    fun handle(action: MemberPostListAction) {
        when (action) {
            is MemberPostListAction.OnPullToRefresh -> postViewModel.refreshPosts()
            is MemberPostListAction.OnWritePostClick -> postViewModel.handleWritePostClick()
            is MemberPostListAction.OnRefreshChange -> postViewModel.setRefreshing(
                action.isRefreshing,
            )

            is MemberPostListAction.OnCommentClick -> handleCommentClick(action.postId)
            MemberPostListAction.OnSubmitCommentClick -> commentViewModel.createComment()
            is MemberPostListAction.OnBottomSheetDismiss -> handleBottomSheetDismiss()
        }
    }

    private fun handleCommentClick(postId: Long) {
        postViewModel.setSelectedPostId(postId)
        commentViewModel.updatePostId(postId)
    }

    private fun handleBottomSheetDismiss() {
        postViewModel.setSelectedPostId(null)
        commentViewModel.updatePostId(null)
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
