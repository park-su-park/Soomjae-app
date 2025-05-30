package com.parksupark.soomjae.features.post.presentation.post.tabs.community

import androidx.paging.PagingData
import com.parksupark.soomjae.features.post.presentation.post.models.CommunityPostUi

internal data class CommunityTabState(
    val isLoading: Boolean = false,
    val posts: PagingData<CommunityPostUi> = PagingData.empty(),
)

sealed interface CommunityTabAction {
    data object OnClick : CommunityTabAction
}
