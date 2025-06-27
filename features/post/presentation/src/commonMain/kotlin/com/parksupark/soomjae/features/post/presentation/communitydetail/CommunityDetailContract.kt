package com.parksupark.soomjae.features.post.presentation.communitydetail

import com.parksupark.soomjae.core.presentation.ui.utils.UiText
import com.parksupark.soomjae.features.post.presentation.communitydetail.models.CommunityPostDetailUi

internal sealed interface CommunityDetailState {
    data class Error(val error: UiText) : CommunityDetailState

    data class InitialLoading(val postId: Long) : CommunityDetailState

    data class Success(val postDetail: CommunityPostDetailUi) : CommunityDetailState
}

internal sealed interface CommunityDetailAction {
    data object OnBackClick : CommunityDetailAction

    data object OnToggleLikeClick : CommunityDetailAction
}

internal sealed interface CommunityDetailEvent {
    data class Error(val message: UiText) : CommunityDetailEvent
}
