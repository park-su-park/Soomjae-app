package com.parksupark.soomjae.features.auth.presentation.register

import androidx.compose.foundation.text.input.TextFieldState
import com.parksupark.soomjae.core.presentation.ui.utils.UiText
import com.parksupark.soomjae.features.auth.domain.PasswordValidationState

data class RegisterState(
    val email: TextFieldState = TextFieldState(),
    val inputPassword: TextFieldState = TextFieldState(),
    val passwordValidationState: PasswordValidationState = PasswordValidationState(),
    val inputConfirmPassword: TextFieldState = TextFieldState(),
    val isPasswordMatch: Boolean = false,
    val canRegister: Boolean = false,
    val isRegistering: Boolean = false,
)

sealed interface RegisterAction {
    data object OnBackClick : RegisterAction

    data object OnRegisterClick : RegisterAction

    data object OnLoginClick : RegisterAction
}

sealed interface RegisterEvent {
    data class RegistrationSuccess(val email: String) : RegisterEvent

    data class Error(val error: UiText) : RegisterEvent
}
