package com.parksupark.soomjae.features.posts.member.presentation.post_list

import androidx.paging.PagingData
import com.parksupark.soomjae.features.posts.member.presentation.post_list.models.MemberPostUi

data class MemberPostListState(
    val isPostsRefreshing: Boolean = false,
    val posts: PagingData<MemberPostUi> = PagingData.empty(),
)

sealed interface MemberPostListAction {
    data object OnPullToRefresh : MemberPostListAction

    data object OnWritePostClick : MemberPostListAction

    data class OnRefreshChange(val isRefreshing: Boolean) : MemberPostListAction
}

sealed interface MemberPostListEvent {
    data object NavigateToWritePost : MemberPostListEvent

    data object RefreshPosts : MemberPostListEvent
}
