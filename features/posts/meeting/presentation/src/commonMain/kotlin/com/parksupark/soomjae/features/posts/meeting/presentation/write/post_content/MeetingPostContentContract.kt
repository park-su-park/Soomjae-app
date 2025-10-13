package com.parksupark.soomjae.features.posts.meeting.presentation.write.post_content

import androidx.compose.foundation.text.input.TextFieldState
import com.parksupark.soomjae.features.posts.common.presentation.models.CategoryUi
import com.parksupark.soomjae.features.posts.common.presentation.models.LocationUi
import com.parksupark.soomjae.features.posts.meeting.presentation.models.MeetingCreationUi
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class MeetingPostContentState(
    val inputTitle: TextFieldState = TextFieldState(),
    val inputContent: TextFieldState = TextFieldState(),
    val meeting: MeetingCreationUi? = null,
    val categories: ImmutableList<CategoryUi> = persistentListOf(),
    val selectedCategory: CategoryUi? = null,
    val locations: ImmutableList<LocationUi> = persistentListOf(),
    val selectedLocation: LocationUi? = null,
    val isSubmitting: Boolean = false,
    val canSubmit: Boolean = false,
)
