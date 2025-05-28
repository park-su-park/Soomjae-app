package com.parksupark.soomjae.features.profile.presentation.profile

sealed interface ProfileState {
    data class MyProfileState(
        val isLogin: Boolean = false,
    ) : ProfileState

    data class OtherProfileState(
        val userId: String = "",
    ) : ProfileState
}

sealed interface ProfileAction {
    data object OnLoginClick : ProfileAction

    data object OnSettingClick : ProfileAction
}
