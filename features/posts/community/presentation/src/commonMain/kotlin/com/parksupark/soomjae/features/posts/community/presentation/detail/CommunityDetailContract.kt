package com.parksupark.soomjae.features.posts.community.presentation.detail

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.parksupark.soomjae.core.presentation.ui.utils.UiText
import com.parksupark.soomjae.features.posts.community.presentation.models.CommunityPostDetailUi

sealed interface CommunityDetailState {
    @Immutable
    data class Error(val error: UiText) : CommunityDetailState

    @Immutable
    data class InitialLoading(val postId: Long) : CommunityDetailState

    @Stable
    data class Success(
        val postDetail: CommunityPostDetailUi,
        val isMine: Boolean = false,
        val isLikeLoading: Boolean = false,
        val inputCommentState: TextFieldState = TextFieldState(),
        val isCommentSubmitting: Boolean = false,
        val isLoggedIn: Boolean = false,
        val isDeleteLoading: Boolean = false,
    ) : CommunityDetailState
}

sealed interface CommunityDetailAction {
    data object OnBackClick : CommunityDetailAction

    data object OnDeleteClick : CommunityDetailAction

    data object OnEditClick : CommunityDetailAction

    data object OnToggleLikeClick : CommunityDetailAction

    data object OnSendCommentClick : CommunityDetailAction

    data object LoginRequest : CommunityDetailAction
}

sealed interface CommunityDetailEvent {
    data class Error(val message: UiText) : CommunityDetailEvent

    data object PostDeleted : CommunityDetailEvent
}
