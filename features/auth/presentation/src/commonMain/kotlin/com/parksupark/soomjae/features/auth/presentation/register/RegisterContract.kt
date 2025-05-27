package com.parksupark.soomjae.features.auth.presentation.register

import androidx.compose.foundation.text.input.TextFieldState

data class RegisterState(
    val inputEmail: TextFieldState = TextFieldState(),
    val inputPassword: TextFieldState = TextFieldState(),
    val inputPasswordConfirm: TextFieldState = TextFieldState(),
)

sealed interface RegisterAction {
    data object OnBackClick : RegisterAction

    data object OnRegisterClick : RegisterAction

    data object OnLoginClick : RegisterAction
}
