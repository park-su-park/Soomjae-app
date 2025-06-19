package com.parksupark.soomjae.features.setting.presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parksupark.soomjae.core.domain.model.ColorTheme
import com.parksupark.soomjae.core.domain.repository.ColorThemeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class SettingViewModel(
    private val colorThemeRepository: ColorThemeRepository,
) : ViewModel() {
    private val _stateFlow: MutableStateFlow<SettingState> = MutableStateFlow(SettingState())
    val stateFlow: StateFlow<SettingState> = _stateFlow.asStateFlow()

    init {
        colorThemeRepository.getColorThemeStream().onEach { theme ->
            _stateFlow.update { it.copy(colorTheme = theme) }
        }.launchIn(viewModelScope)
    }

    fun changeColorTheme(theme: ColorTheme) {
        if (_stateFlow.value.colorTheme == theme) return

        viewModelScope.launch {
            colorThemeRepository.setColorTheme(theme)
        }
    }
}
