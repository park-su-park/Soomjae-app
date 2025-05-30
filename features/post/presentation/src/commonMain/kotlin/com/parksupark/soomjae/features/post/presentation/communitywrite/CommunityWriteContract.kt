package com.parksupark.soomjae.features.post.presentation.communitywrite

import androidx.compose.foundation.text.input.TextFieldState
import com.mohamedrejeb.richeditor.model.RichTextState

data class CommunityWriteState(
    val inputTitle: TextFieldState = TextFieldState(),
    val inputContent: RichTextState = RichTextState(),
)

sealed interface CommunityWriteAction {
    data object OnBackClick : CommunityWriteAction

    data object OnConfirmClick : CommunityWriteAction
}
