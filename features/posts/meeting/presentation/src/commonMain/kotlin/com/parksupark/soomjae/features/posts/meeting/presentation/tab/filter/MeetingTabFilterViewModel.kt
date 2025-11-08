package com.parksupark.soomjae.features.posts.meeting.presentation.tab.filter

import androidx.lifecycle.ViewModel
import com.parksupark.soomjae.core.presentation.ui.utils.loadIfNeeded
import com.parksupark.soomjae.features.posts.common.domain.repositories.CategoryRepository
import com.parksupark.soomjae.features.posts.common.domain.repositories.LocationRepository
import com.parksupark.soomjae.features.posts.common.presentation.models.CategoryUi
import com.parksupark.soomjae.features.posts.common.presentation.models.LocationUi
import com.parksupark.soomjae.features.posts.common.presentation.models.toLocationUi
import com.parksupark.soomjae.features.posts.common.presentation.models.toUi
import com.parksupark.soomjae.features.posts.meeting.presentation.models.RecruitmentStatusUi
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.collections.immutable.toPersistentSet
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

class MeetingTabFilterViewModel(
    private val categoryRepository: CategoryRepository,
    private val locationRepository: LocationRepository,
) : ViewModel() {
    private val eventChannel = Channel<MeetingTabFilterEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _stateFlow: MutableStateFlow<MeetingTabFilterState> =
        MutableStateFlow(MeetingTabFilterState())
    val stateFlow: StateFlow<MeetingTabFilterState> = _stateFlow.asStateFlow()

    fun loadCategoriesIfNeeded() {
        loadIfNeeded(
            stateFlow = _stateFlow,
            isLoaded = { it.isCategoryLoaded },
            setLoading = { state, isLoading -> state.copy(isCategoryLoading = isLoading) },
            load = { categoryRepository.getAllCategories() },
            onSuccess = { state, categories ->
                state.copy(
                    categories = categories.mapValues { (_, category) ->
                        category.toUi()
                    }.toImmutableMap(),
                    isCategoryLoaded = true,
                )
            },
            onFailure = { state, _ -> state },
        )
    }

    fun updateSelectedCategories(categories: List<CategoryUi>) {
        _stateFlow.update { state ->
            val selectedCategories =
                categories.mapNotNull { category -> state.categories[category.id] }
                    .toPersistentSet()
            state.copy(selectedOption = state.selectedOption.copy(categories = selectedCategories))
        }
    }

    fun loadLocationsIfNeeded() {
        loadIfNeeded(
            stateFlow = _stateFlow,
            isLoaded = { it.isLocationLoaded },
            setLoading = { state, isLoading -> state.copy(isLocationLoading = isLoading) },
            load = { locationRepository.getAllLocations() },
            onSuccess = { state, locations ->
                state.copy(
                    locations = locations.map { it.toLocationUi() }
                        .associateBy { it.code }
                        .toImmutableMap(),
                    isLocationLoaded = true,
                )
            },
            onFailure = { state, _ -> state },
        )
    }

    fun updateSelectedLocations(locations: List<LocationUi>) {
        _stateFlow.update { state ->
            val selectedLocations =
                locations.mapNotNull { location -> state.locations[location.code] }
                    .toPersistentSet()
            state.copy(selectedOption = state.selectedOption.copy(locations = selectedLocations))
        }
    }

    fun updateSelectedStatuses(statuses: List<RecruitmentStatusUi>) {
        _stateFlow.update { state ->
            val selectedStatuses = statuses.toPersistentSet()
            state.copy(
                selectedOption = state.selectedOption.copy(recruitmentStatuses = selectedStatuses),
            )
        }
    }
}
