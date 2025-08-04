package com.parksupark.soomjae.features.posts.meeting.presentation.write

import androidx.compose.foundation.text.input.TextFieldState
import com.parksupark.soomjae.features.posts.common.presentation.models.CategoryUi
import com.parksupark.soomjae.features.posts.common.presentation.models.LocationUi
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal data class MeetingWriteState(
    val inputTitle: TextFieldState = TextFieldState(),
    val inputContent: TextFieldState = TextFieldState(),
    val categories: ImmutableList<CategoryUi> = persistentListOf(),
    val selectedCategory: CategoryUi? = null,
    val locations: ImmutableList<LocationUi> = persistentListOf(),
    val selectedLocation: LocationUi? = null,
    val canSubmit: Boolean = false,
)

internal sealed interface MeetingWriteAction {
    data object OnBackClick : MeetingWriteAction

    data object OnConfirmClick : MeetingWriteAction

    data class OnCategorySelect(val categoryId: Long) : MeetingWriteAction

    data class OnLocationSelect(val locationCode: Long) : MeetingWriteAction
}
