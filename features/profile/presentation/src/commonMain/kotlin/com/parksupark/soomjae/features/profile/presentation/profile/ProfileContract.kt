package com.parksupark.soomjae.features.profile.presentation.profile

class ProfileState

sealed interface ProfileAction {
    data object OnClick : ProfileAction
}
