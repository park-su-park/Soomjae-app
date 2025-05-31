package com.parksupark.soomjae.features.post.presentation.communitydetail

import com.parksupark.soomjae.core.presentation.ui.utils.UiText

sealed interface CommunityDetailState {
    data class Error(val error: UiText) : CommunityDetailState

    data class InitialLoading(val postId: String) : CommunityDetailState

    data class Success(val postId: String) : CommunityDetailState
}

sealed interface CommunityDetailAction {
    data object OnClick : CommunityDetailAction
}

sealed interface CommunityDetailEvent {
    data class Error(val message: UiText) : CommunityDetailEvent
}
