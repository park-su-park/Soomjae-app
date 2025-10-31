package com.parksupark.soomjae.features.posts.community.presentation.tab.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parksupark.soomjae.features.posts.common.domain.repositories.CategoryRepository
import com.parksupark.soomjae.features.posts.common.domain.repositories.LocationRepository
import com.parksupark.soomjae.features.posts.common.presentation.models.toLocationUi
import com.parksupark.soomjae.features.posts.common.presentation.models.toUi
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.collections.immutable.toPersistentSet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CommunityTabFilterViewModel(
    private val categoryRepository: CategoryRepository,
    private val locationRepository: LocationRepository,
) : ViewModel() {
    private val _stateFlow: MutableStateFlow<CommunityTabFilterState> =
        MutableStateFlow(CommunityTabFilterState())
    val stateFlow: StateFlow<CommunityTabFilterState> = _stateFlow.asStateFlow()

    fun loadCategoriesIfNeeded() {
        val state = stateFlow.value
        if (state.isCategoryLoaded) return

        viewModelScope.launch {
            _stateFlow.update { it.copy(isCategoryLoading = true) }

            categoryRepository.getAllCategories().fold(
                ifLeft = {
                    _stateFlow.update { it.copy(isCategoryLoading = false) }
                },
                ifRight = { categories ->
                    _stateFlow.update { state ->
                        state.copy(
                            categories = categories.mapValues { (_, category) ->
                                category.toUi()
                            }.toImmutableMap(),
                            isCategoryLoaded = true,
                            isCategoryLoading = false,
                        )
                    }
                },
            )
        }
    }

    fun updateSelectedCategories(ids: List<Long>) {
        _stateFlow.update { state ->
            val selectedCategories = ids.mapNotNull { id ->
                state.categories[id]
            }.toPersistentSet()
            state.copy(
                selectedOption = state.selectedOption.copy(
                    categories = selectedCategories,
                ),
            )
        }
    }

    fun loadLocationsIfNeeded() {
        val state = stateFlow.value
        if (state.isLocationLoaded) return

        viewModelScope.launch {
            _stateFlow.update { it.copy(isLocationLoading = true) }

            locationRepository.getAllLocations().fold(
                ifLeft = {
                    _stateFlow.update { it.copy(isLocationLoading = false) }
                },
                ifRight = { locations ->
                    _stateFlow.update { state ->
                        state.copy(
                            locations = locations.associateBy(
                                keySelector = { it.code },
                                valueTransform = { it.toLocationUi() },
                            ).toImmutableMap(),
                            isLocationLoaded = true,
                            isLocationLoading = false,
                        )
                    }
                },
            )
        }
    }

    fun updateSelectedLocations(ids: List<Long>) {
        _stateFlow.update { state ->
            val selectedLocations = ids.mapNotNull { id ->
                state.locations[id]
            }.toPersistentSet()
            state.copy(
                selectedOption = state.selectedOption.copy(
                    locations = selectedLocations,
                ),
            )
        }
    }
}
