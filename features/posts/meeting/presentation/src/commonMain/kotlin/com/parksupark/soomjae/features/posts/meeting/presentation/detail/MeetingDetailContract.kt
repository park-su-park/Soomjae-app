package com.parksupark.soomjae.features.posts.meeting.presentation.detail

import androidx.compose.foundation.text.input.TextFieldState
import com.parksupark.soomjae.features.posts.meeting.presentation.detail.models.MeetingPostDetailUi

sealed interface MeetingDetailState {
    data object Loading : MeetingDetailState

    data class Success(
        val postDetail: MeetingPostDetailUi,
        val isLikeLoading: Boolean = false,
        val inputCommentState: TextFieldState = TextFieldState(),
        val isCommentSubmitting: Boolean = false,
        val isParticipationLoading: Boolean = false,
        val isLoggedIn: Boolean = false,
        val canModify: Boolean = false,
    ) : MeetingDetailState
}

sealed interface MeetingDetailAction {
    data object OnBackClick : MeetingDetailAction

    data object OnToggleLikeClick : MeetingDetailAction

    data object OnSendCommentClick : MeetingDetailAction

    data object OnToggleParticipationClick : MeetingDetailAction

    data object OnMessageClick : MeetingDetailAction

    data object LoginRequest : MeetingDetailAction

    data object OnEditClick : MeetingDetailAction

    data object OnDeleteClick : MeetingDetailAction
}

sealed interface MeetingDetailEvent {
    data class NavigateToEditPost(val postId: Long) : MeetingDetailEvent

    data object PostDeleted : MeetingDetailEvent
}
