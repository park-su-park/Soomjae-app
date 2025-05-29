package com.parksupark.soomjae.features.auth.presentation.emaillogin

class EmailLoginState

sealed interface EmailLoginAction {
    data object OnClick : EmailLoginAction
}
