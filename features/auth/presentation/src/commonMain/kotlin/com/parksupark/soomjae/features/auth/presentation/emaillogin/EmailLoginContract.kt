package com.parksupark.soomjae.features.auth.presentation.emaillogin

import androidx.compose.foundation.text.input.TextFieldState
import com.parksupark.soomjae.core.presentation.ui.utils.UiText

data class EmailLoginState(
    val inputEmail: TextFieldState = TextFieldState(),
    val isEmailValid: Boolean = false,
    val inputPassword: TextFieldState = TextFieldState(),
    val isPasswordValid: Boolean = false,
    val shouldSaveEmail: Boolean = false,
    val canLogin: Boolean = false,
    val isLoggingIn: Boolean = false,
)

sealed interface EmailLoginAction {
    data object OnBackClick : EmailLoginAction

    data object OnLoginClick : EmailLoginAction

    data object OnSaveEmailClick : EmailLoginAction
}

sealed interface EmailLoginEvent {
    data class Error(val message: UiText) : EmailLoginEvent

    data object LoginSuccess : EmailLoginEvent
}
