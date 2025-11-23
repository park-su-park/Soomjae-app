package com.parksupark.soomjae.features.profile.presentation.introduction_edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class IntroductionEditViewModel(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _stateFlow: MutableStateFlow<IntroductionEditState> =
        MutableStateFlow(IntroductionEditState())

    val stateFlow: StateFlow<IntroductionEditState> = _stateFlow.asStateFlow()
}
