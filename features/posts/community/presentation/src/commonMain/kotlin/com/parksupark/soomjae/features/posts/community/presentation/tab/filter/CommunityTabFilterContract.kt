package com.parksupark.soomjae.features.posts.community.presentation.tab.filter

import com.parksupark.soomjae.features.posts.common.presentation.models.CategoryUi
import com.parksupark.soomjae.features.posts.common.presentation.models.LocationUi
import com.parksupark.soomjae.features.posts.community.presentation.models.CommunityFilterOption
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentMapOf

data class CommunityTabFilterState(
    val categories: ImmutableMap<Long, CategoryUi> = persistentMapOf(),
    val isCategoryLoaded: Boolean = false,
    val isCategoryLoading: Boolean = false,
    val locations: ImmutableMap<Long, LocationUi> = persistentMapOf(),
    val isLocationLoaded: Boolean = false,
    val isLocationLoading: Boolean = false,
    val selectedOption: CommunityFilterOption = CommunityFilterOption(),
)

sealed interface CommunityTabFilterAction

sealed interface CommunityTabFilterEvent
