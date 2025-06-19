package com.parksupark.soomjae.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parksupark.soomjae.core.domain.repository.ColorThemeRepository
import com.parksupark.soomjae.navigation.mainNavigationBarItems
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

internal class SoomjaeViewModel(
    colorThemeRepository: ColorThemeRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        SoomjaeState(
            navigationBarItems = mainNavigationBarItems,
        ),
    )
    val uiState = _uiState.asStateFlow()

    init {
        colorThemeRepository.getColorThemeStream().onEach { theme ->
            _uiState.update { it.copy(theme = theme) }
        }.launchIn(viewModelScope)
    }
}
