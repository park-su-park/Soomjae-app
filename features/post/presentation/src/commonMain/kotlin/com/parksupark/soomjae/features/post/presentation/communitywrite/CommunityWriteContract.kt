package com.parksupark.soomjae.features.post.presentation.communitywrite

import androidx.compose.foundation.text.input.TextFieldState
import com.mohamedrejeb.richeditor.model.RichTextState

data class CommunityWriteState(
    val inputTitle: TextFieldState = TextFieldState(),
    val isTitleValid: Boolean = false,
    val inputContent: RichTextState = RichTextState(),
    val isContentValid: Boolean = false,
    val isSubmitting: Boolean = false,
    val canSubmit: Boolean = false,
)

sealed interface CommunityWriteAction {
    data object OnBackClick : CommunityWriteAction

    data object OnConfirmClick : CommunityWriteAction
}
