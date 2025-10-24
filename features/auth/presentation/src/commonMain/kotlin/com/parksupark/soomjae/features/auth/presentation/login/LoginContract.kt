package com.parksupark.soomjae.features.auth.presentation.login

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.presentation.ui.utils.UiText
import com.parksupark.soomjae.features.auth.libs.google.models.GoogleUser

internal class LoginState

internal sealed interface LoginAction {
    data object OnCloseClick : LoginAction

    data object OnRegisterClick : LoginAction

    data object OnEmailLoginClick : LoginAction

    data class GoogleLoginResult(val result: Either<DataFailure.Credential, GoogleUser>) :
        LoginAction
}

internal sealed interface LoginEvent {
    data object OnLoginSuccess : LoginEvent

    data class Error(val message: UiText) : LoginEvent
}
