package com.parksupark.soomjae.features.auth.presentation.login

class LoginState

sealed interface LoginAction {
    data object OnCloseClick : LoginAction

    data object OnRegisterClick : LoginAction

    data object OnEmailLoginClick : LoginAction
}
