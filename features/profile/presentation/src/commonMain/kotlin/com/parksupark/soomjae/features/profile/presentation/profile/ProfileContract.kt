package com.parksupark.soomjae.features.profile.presentation.profile

import androidx.compose.runtime.Immutable
import com.parksupark.soomjae.features.profile.presentation.profile.model.UserUi

internal sealed interface ProfileState {
    @Immutable
    data class MyProfileState(
        val isLoading: Boolean = true,
        val isLoggedIn: Boolean = false,
        val user: UserUi = UserUi.Default,
    ) : ProfileState

    @Immutable
    data class OtherProfileState(
        val userId: String = "",
    ) : ProfileState
}

sealed interface ProfileAction {
    data object OnLoginClick : ProfileAction

    data object OnLogoutClick : ProfileAction

    data object OnSettingClick : ProfileAction
}
