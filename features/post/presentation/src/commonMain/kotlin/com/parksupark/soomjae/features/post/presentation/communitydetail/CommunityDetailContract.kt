package com.parksupark.soomjae.features.post.presentation.communitydetail

import com.parksupark.soomjae.core.presentation.ui.utils.UiText
import com.parksupark.soomjae.features.post.presentation.post.models.CommunityPostUi

internal sealed interface CommunityDetailState {
    data class Error(val error: UiText) : CommunityDetailState

    data class InitialLoading(val postId: String) : CommunityDetailState

    data class Success(val postDetail: CommunityPostUi) : CommunityDetailState
}

internal sealed interface CommunityDetailAction {
    data object OnClick : CommunityDetailAction
}

internal sealed interface CommunityDetailEvent {
    data class Error(val message: UiText) : CommunityDetailEvent
}
