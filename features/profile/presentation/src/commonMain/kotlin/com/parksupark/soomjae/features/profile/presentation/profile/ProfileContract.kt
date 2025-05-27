package com.parksupark.soomjae.features.profile.presentation.profile

class ProfileState

sealed interface ProfileAction {
    data object OnLoginClick : ProfileAction

    data object OnSettingClick : ProfileAction
}
