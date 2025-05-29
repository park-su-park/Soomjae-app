package com.parksupark.soomjae.features.auth.presentation.emaillogin

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class EmailLoginViewModel : ViewModel() {
    private val _uiStateFlow: MutableStateFlow<EmailLoginState> = MutableStateFlow(EmailLoginState())
    val uiStateFlow: StateFlow<EmailLoginState> = _uiStateFlow.asStateFlow()
}
