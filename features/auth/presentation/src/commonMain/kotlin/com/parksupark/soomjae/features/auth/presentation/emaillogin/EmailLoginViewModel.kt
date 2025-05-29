package com.parksupark.soomjae.features.auth.presentation.emaillogin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parksupark.soomjae.core.presentation.ui.utils.collectAsFlow
import com.parksupark.soomjae.features.auth.domain.AuthRepository
import com.parksupark.soomjae.features.auth.domain.UserDataValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class EmailLoginViewModel(
    private val userDataValidator: UserDataValidator,
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _uiStateFlow: MutableStateFlow<EmailLoginState> = MutableStateFlow(EmailLoginState())
    val uiStateFlow: StateFlow<EmailLoginState> = _uiStateFlow.asStateFlow()

    init {
        uiStateFlow.value.inputEmail.collectAsFlow().onEach { email ->
            _uiStateFlow.update {
                val isEmailValid = userDataValidator.isValidEmail(email.toString())
                it.copy(isEmailValid = isEmailValid)
            }
        }.launchIn(viewModelScope)

        uiStateFlow.value.inputPassword.collectAsFlow().onEach { password ->
            _uiStateFlow.update {
                val isPasswordValid = password.toString().isNotBlank()
                it.copy(isPasswordValid = isPasswordValid)
            }
        }.launchIn(viewModelScope)

        uiStateFlow.distinctUntilChanged { old, new ->
            old.isEmailValid == new.isEmailValid &&
                old.isPasswordValid == new.isPasswordValid
        }.map { state ->
            state.isEmailValid && state.isPasswordValid
        }.onEach { canLogin ->
            _uiStateFlow.update {
                it.copy(canLogin = canLogin)
            }
        }.launchIn(viewModelScope)
    }

    fun login() {
        // TODO: Implement login logic
    }
}
