package com.parksupark.soomjae.features.posts.member.presentation.post_list.comment

import androidx.compose.foundation.text.input.TextFieldState
import com.parksupark.soomjae.core.presentation.ui.utils.UiText
import com.parksupark.soomjae.features.posts.common.presentation.models.CommentUi
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class MemberPostCommentState(
    val postId: Long? = null,
    val isLoading: Boolean = true,
    val comments: ImmutableList<CommentUi> = persistentListOf(),
    val isCommentSubmitting: Boolean = false,
    val inputComments: TextFieldState = TextFieldState(),
)

sealed interface MemberPostCommentEvent {
    data object CommentSubmissionSuccess : MemberPostCommentEvent

    data class Error(val message: UiText) : MemberPostCommentEvent
}
