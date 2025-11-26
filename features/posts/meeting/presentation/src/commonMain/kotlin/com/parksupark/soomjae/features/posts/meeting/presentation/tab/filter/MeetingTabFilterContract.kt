package com.parksupark.soomjae.features.posts.meeting.presentation.tab.filter

import com.parksupark.soomjae.core.presentation.ui.post.model.CategoryUi
import com.parksupark.soomjae.core.presentation.ui.post.model.LocationUi
import com.parksupark.soomjae.core.presentation.ui.utils.UiText
import com.parksupark.soomjae.features.posts.meeting.presentation.models.MeetingTabFilterOption
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentMapOf

data class MeetingTabFilterState(
    val categories: ImmutableMap<Long, CategoryUi> = persistentMapOf(),
    val isCategoryLoaded: Boolean = false,
    val isCategoryLoading: Boolean = false,
    val locations: ImmutableMap<Long, LocationUi> = persistentMapOf(),
    val isLocationLoaded: Boolean = false,
    val isLocationLoading: Boolean = false,
    val selectedOption: MeetingTabFilterOption = MeetingTabFilterOption(),
)

sealed interface MeetingTabFilterEvent {
    data class OnError(val message: UiText) : MeetingTabFilterEvent
}
