package com.parksupark.soomjae.features.posts.community.presentation.write

import androidx.compose.foundation.text.input.TextFieldState
import com.parksupark.soomjae.core.presentation.ui.utils.UiText
import com.parksupark.soomjae.features.posts.common.presentation.models.CategoryUi
import com.parksupark.soomjae.features.posts.common.presentation.models.LocationUi
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal data class CommunityWriteState(
    val inputTitle: TextFieldState = TextFieldState(),
    val isTitleValid: Boolean = false,
    val inputContent: TextFieldState = TextFieldState(),
    val isContentValid: Boolean = false,
    val categories: ImmutableList<CategoryUi> = persistentListOf(),
    val selectedCategory: CategoryUi? = null,
    val locations: ImmutableList<LocationUi> = persistentListOf(),
    val selectedLocation: LocationUi? = null,
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
    data class CategoryError(val message: UiText) : CommunityWriteEvent
    data class LocationError(val message: UiText) : CommunityWriteEvent

    data class PostSubmitted(val postId: Long) : CommunityWriteEvent
}
