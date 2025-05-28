package com.parksupark.soomjae.features.auth.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parksupark.soomjae.core.presentation.ui.utils.collectAsFlow
import com.parksupark.soomjae.features.auth.domain.UserDataValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class RegisterViewModel(
    private val userDataValidator: UserDataValidator,
) : ViewModel() {
    private val _uiState: MutableStateFlow<RegisterState> = MutableStateFlow(RegisterState())
    val uiState: StateFlow<RegisterState> = _uiState.asStateFlow()

    init {
        uiState.value.inputEmail.collectAsFlow().onEach { email ->
            val isValidEmail = userDataValidator.isValidEmail(email.toString())
            _uiState.update {
                it.copy(isEmailValid = isValidEmail)
            }
        }.launchIn(viewModelScope)

        val inputPasswordFlow = uiState.value.inputPassword.collectAsFlow()
        inputPasswordFlow.onEach { password ->
            val passwordValidationState = userDataValidator.validatePassword(password.toString())
            _uiState.update {
                it.copy(passwordValidationState = passwordValidationState)
            }
        }.launchIn(viewModelScope)

        combine(
            inputPasswordFlow,
            uiState.value.inputConfirmPassword.collectAsFlow(),
        ) { (password, confirmPassword) ->
            val isPasswordMatch = userDataValidator.isPasswordMatch(
                password = password.toString(),
                confirmPassword = confirmPassword.toString(),
            )
            _uiState.update {
                it.copy(isPasswordMatch = isPasswordMatch)
            }
        }.launchIn(viewModelScope)

        uiState.distinctUntilChanged { old, new ->
            old.isEmailValid == new.isEmailValid &&
                old.passwordValidationState == new.passwordValidationState &&
                old.isPasswordMatch == new.isPasswordMatch &&
                old.isRegistering == new.isRegistering
        }.map { state ->
            state.isEmailValid && state.passwordValidationState.isValidPassword && state.isPasswordMatch && !state.isRegistering
        }.onEach { canRegister ->
            _uiState.update {
                it.copy(canRegister = canRegister)
            }
        }.launchIn(viewModelScope)
    }
}
