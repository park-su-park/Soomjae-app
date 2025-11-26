package com.parksupark.soomjae.features.home.presentation.home

class HomeState

sealed interface HomeEvent

sealed interface HomeAction {
    data object OnClick : HomeAction
}
