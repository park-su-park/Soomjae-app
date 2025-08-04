package com.parksupark.soomjae.features.posts.common.presentation.write

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MeetingWriteViewModel(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _stateFlow: MutableStateFlow<MeetingWriteState> = MutableStateFlow(MeetingWriteState())

    internal val stateFlow: StateFlow<MeetingWriteState> = _stateFlow.asStateFlow()

    fun submitPost() {
        // TODO: Implement post submission logic
    }

    fun selectCategory(categoryId: Long) {
        _stateFlow.value = _stateFlow.value.copy(
            selectedCategory = _stateFlow.value.categories.find { it.id == categoryId },
        )
    }

    fun selectLocation(locationCode: Long) {
        _stateFlow.value = _stateFlow.value.copy(
            selectedLocation = _stateFlow.value.locations.find { it.code == locationCode },
        )
    }
}
