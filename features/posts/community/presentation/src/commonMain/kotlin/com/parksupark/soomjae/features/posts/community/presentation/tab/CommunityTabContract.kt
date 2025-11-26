package com.parksupark.soomjae.features.posts.community.presentation.tab

import com.parksupark.soomjae.features.posts.community.presentation.tab.filter.CommunityTabFilterState
import com.parksupark.soomjae.features.posts.community.presentation.tab.post.CommunityTabPostState

data class CommunityTabState(
    val filterState: CommunityTabFilterState = CommunityTabFilterState(),
    val postState: CommunityTabPostState = CommunityTabPostState(),
)

sealed interface CommunityTabAction {
    data object OnPullToRefresh : CommunityTabAction

    data class RefreshChange(val isRefreshing: Boolean) : CommunityTabAction

    data class OnPostClick(val postId: Long) : CommunityTabAction

    data object OnCommunityWriteClick : CommunityTabAction

    data object OnCategoryFilterClick : CommunityTabAction

    data class OnCategoryFilterSelect(val ids: List<Long>) : CommunityTabAction

    data object OnLocationFilterClick : CommunityTabAction

    data class OnLocationFilterSelect(val codes: List<Long>) : CommunityTabAction
}
