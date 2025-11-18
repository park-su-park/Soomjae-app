package com.parksupark.soomjae.features.profile.presentation.profile

import androidx.compose.runtime.Immutable
import com.parksupark.soomjae.features.profile.presentation.profile.model.UserUi

sealed interface ProfileState {
    val isLoading: Boolean
    val user: UserUi

    @Immutable
    data class My(
        override val isLoading: Boolean = true,
        override val user: UserUi = UserUi.Default,
        val isLoggedIn: Boolean = false,
    ) : ProfileState

    @Immutable
    data class Other(
        override val isLoading: Boolean = true,
        override val user: UserUi = UserUi.Default,
    ) : ProfileState
}

sealed interface ProfileAction {
    data object OnLoginClick : ProfileAction

    data object OnLogoutClick : ProfileAction

    data object OnSettingClick : ProfileAction
    data class OnEditProfileClick(val memberId: Long) : ProfileAction
}
