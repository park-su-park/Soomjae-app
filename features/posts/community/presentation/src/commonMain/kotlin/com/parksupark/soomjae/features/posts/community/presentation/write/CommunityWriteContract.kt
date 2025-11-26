package com.parksupark.soomjae.features.posts.community.presentation.write

import androidx.compose.foundation.text.input.TextFieldState
import com.parksupark.soomjae.core.presentation.ui.post.model.CategoryUi
import com.parksupark.soomjae.core.presentation.ui.post.model.LocationUi
import com.parksupark.soomjae.core.presentation.ui.utils.UiText
import com.parksupark.soomjae.features.posts.community.domain.model.CommunityPostDetail
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class CommunityWriteState(
    val mode: WriteMode = WriteMode.Create,

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
) {
    sealed interface WriteMode {
        data object Create : WriteMode

        data class Edit(
            val postId: Long,
            val originalPost: CommunityPostDetail?,
        ) : WriteMode
    }
}

internal sealed interface CommunityWriteAction {
    data object OnBackClick : CommunityWriteAction

    data object OnConfirmClick : CommunityWriteAction

    data class OnCategorySelected(val categoryId: Long) : CommunityWriteAction

    data class OnLocationSelected(val locationCode: Long) : CommunityWriteAction
}

internal sealed interface CommunityWriteEvent {
    data class PostError(val message: UiText) : CommunityWriteEvent

    data class CategoryError(val message: UiText) : CommunityWriteEvent

    data class LocationError(val message: UiText) : CommunityWriteEvent

    data class PostSubmitted(val postId: Long) : CommunityWriteEvent
}
