package com.parksupark.soomjae.features.auth.presentation.login

internal class LoginState

internal sealed interface LoginAction {
    data object OnCloseClick : LoginAction

    data object OnRegisterClick : LoginAction

    data object OnEmailLoginClick : LoginAction
}
