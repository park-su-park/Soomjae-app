package com.parksupark.soomjae.features.posts.meeting.presentation.write.compose

import androidx.compose.foundation.text.input.TextFieldState
import com.parksupark.soomjae.features.posts.common.presentation.models.CategoryUi
import com.parksupark.soomjae.features.posts.common.presentation.models.LocationUi
import com.parksupark.soomjae.features.posts.meeting.presentation.models.MeetingCreateUi
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class MeetingComposeState(
    val inputTitle: TextFieldState = TextFieldState(),
    val inputContent: TextFieldState = TextFieldState(),
    val meeting: MeetingCreateUi? = null,
    val categories: ImmutableList<CategoryUi> = persistentListOf(),
    val selectedCategory: CategoryUi? = null,
    val locations: ImmutableList<LocationUi> = persistentListOf(),
    val selectedLocation: LocationUi? = null,
    val canSubmit: Boolean = false,
)
