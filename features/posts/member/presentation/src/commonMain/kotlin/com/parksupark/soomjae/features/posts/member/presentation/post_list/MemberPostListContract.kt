package com.parksupark.soomjae.features.posts.member.presentation.post_list

import com.parksupark.soomjae.features.posts.member.presentation.post_list.comment.MemberPostCommentEvent

data class MemberPostListState(
    val isPostsRefreshing: Boolean = false,
    val selectedPostId: Long? = null,
)

sealed interface MemberPostListAction {
    data object OnPullToRefresh : MemberPostListAction

    data object OnWritePostClick : MemberPostListAction

    data class OnRefreshChange(val isRefreshing: Boolean) : MemberPostListAction

    data class OnCommentClick(val postId: Long) : MemberPostListAction

    data object OnBottomSheetDismiss : MemberPostListAction

    data object OnSubmitCommentClick : MemberPostListAction
}

sealed interface MemberPostListEvent {
    data object NavigateToWritePost : MemberPostListEvent

    data object RefreshPosts : MemberPostListEvent

    data class FromComment(val commentEvent: MemberPostCommentEvent) : MemberPostListEvent
}
