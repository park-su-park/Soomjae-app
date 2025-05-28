package com.parksupark.soomjae.features.profile.presentation.profile

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileViewModel : ViewModel() {
    private val _uiStateFlow: MutableStateFlow<ProfileState> = MutableStateFlow(ProfileState.MyProfileState())
    val uiStateFlow: StateFlow<ProfileState> = _uiStateFlow.asStateFlow()
}
