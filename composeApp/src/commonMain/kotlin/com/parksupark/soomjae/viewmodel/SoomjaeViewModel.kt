package com.parksupark.soomjae.viewmodel

import androidx.lifecycle.ViewModel
import com.parksupark.soomjae.navigation.mainNavigationBarItems
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class SoomjaeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(
        SoomjaeState(
            navigationBarItems = mainNavigationBarItems,
        ),
    )
    val uiState = _uiState.asStateFlow()
}
