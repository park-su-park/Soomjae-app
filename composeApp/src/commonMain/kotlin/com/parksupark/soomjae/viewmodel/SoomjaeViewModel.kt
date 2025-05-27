package com.parksupark.soomjae.viewmodel

import androidx.lifecycle.ViewModel
import com.parksupark.soomjae.navigation.mainNavigationBarItemItems
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class SoomjaeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(
        SoomjaeState(
            navigationBarItems = mainNavigationBarItemItems,
        ),
    )
    val uiState = _uiState.asStateFlow()
}
