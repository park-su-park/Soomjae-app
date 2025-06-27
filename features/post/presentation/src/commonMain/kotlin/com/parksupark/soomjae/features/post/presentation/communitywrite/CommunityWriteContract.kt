package com.parksupark.soomjae.features.post.presentation.communitywrite

import androidx.compose.foundation.text.input.TextFieldState
import com.mohamedrejeb.richeditor.model.RichTextState
import com.parksupark.soomjae.core.presentation.ui.utils.UiText
import com.parksupark.soomjae.features.post.presentation.models.CategoryUi
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal data class CommunityWriteState(
    val inputTitle: TextFieldState = TextFieldState(),
    val isTitleValid: Boolean = false,
    val inputContent: RichTextState = RichTextState(),
    val isContentValid: Boolean = false,
    val categories: ImmutableList<CategoryUi> = persistentListOf(),
    val selectedCategory: CategoryUi? = null,
    val canSubmit: Boolean = false,

    val isSubmitting: Boolean = false,
    val isCategoryLoading: Boolean = true,
)

internal sealed interface CommunityWriteAction {
    data object OnBackClick : CommunityWriteAction

    data object OnConfirmClick : CommunityWriteAction

    data class OnCategorySelected(val categoryId: Long) : CommunityWriteAction
}

internal sealed interface CommunityWriteEvent {
    data class Error(val message: UiText) : CommunityWriteEvent

    data class PostSubmitted(val postId: Long) : CommunityWriteEvent
}
