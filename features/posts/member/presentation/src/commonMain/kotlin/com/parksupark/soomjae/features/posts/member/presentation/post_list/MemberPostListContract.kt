package com.parksupark.soomjae.features.posts.member.presentation.post_list

import androidx.paging.PagingData
import com.parksupark.soomjae.features.posts.member.presentation.post_list.models.MemberPostUi

data class MemberPostListState(
    val isPostsRefreshing: Boolean = false,
    val posts: PagingData<MemberPostUi> = PagingData.empty(),
    val selectedPostId: Long? = null,
)

sealed interface MemberPostListAction {
    data object OnPullToRefresh : MemberPostListAction

    data object OnWritePostClick : MemberPostListAction

    data class OnRefreshChange(val isRefreshing: Boolean) : MemberPostListAction

    data class OnCommentClick(val postId: Long) : MemberPostListAction

    data object OnBottomSheetDismiss : MemberPostListAction
}

sealed interface MemberPostListEvent {
    data object NavigateToWritePost : MemberPostListEvent

    data object RefreshPosts : MemberPostListEvent
}
