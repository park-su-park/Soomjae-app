package com.parksupark.soomjae.features.setting.presentation.setting

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class SettingViewModel(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _stateFlow: MutableStateFlow<SettingState> = MutableStateFlow(SettingState())

    val stateFlow: StateFlow<SettingState> = _stateFlow.asStateFlow()
}
