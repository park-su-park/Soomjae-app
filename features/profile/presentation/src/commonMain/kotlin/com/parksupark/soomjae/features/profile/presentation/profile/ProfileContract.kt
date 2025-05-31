package com.parksupark.soomjae.features.profile.presentation.profile

import com.parksupark.soomjae.features.profile.presentation.profile.mdoels.UserUi

internal sealed interface ProfileState {
    data class MyProfileState(
        val isLoggedIn: Boolean = false,
        val user: UserUi = UserUi.Default,
    ) : ProfileState

    data class OtherProfileState(
        val userId: String = "",
    ) : ProfileState
}

sealed interface ProfileAction {
    data object OnLoginClick : ProfileAction

    data object OnSettingClick : ProfileAction
}
