package com.parksupark.soomjae.features.posts.community.presentation.detail

import com.parksupark.soomjae.core.presentation.ui.utils.UiText
import com.parksupark.soomjae.features.posts.community.presentation.models.CommunityPostDetailUi

sealed interface CommunityDetailState {
    data class Error(val error: UiText) : CommunityDetailState

    data class InitialLoading(val postId: Long) : CommunityDetailState

    data class Success(
        val postDetail: CommunityPostDetailUi,
        val isLikeLoading: Boolean = false,
    ) : CommunityDetailState
}

sealed interface CommunityDetailAction {
    data object OnBackClick : CommunityDetailAction

    data object OnToggleLikeClick : CommunityDetailAction
}

internal sealed interface CommunityDetailEvent {
    data class Error(val message: UiText) : CommunityDetailEvent
}
