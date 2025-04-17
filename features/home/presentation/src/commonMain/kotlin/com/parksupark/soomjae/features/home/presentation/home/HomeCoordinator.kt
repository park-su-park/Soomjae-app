package com.parksupark.soomjae.features.home.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.koin.compose.viewmodel.koinViewModel

internal class HomeCoordinator(
    val viewModel: HomeViewModel,
) {
    val uiStateFlow = viewModel.uiState
    val eventChannel = viewModel.event

    fun handle(action: HomeAction) {
        when (action) {
            HomeAction.OnClick -> { // Handle action
            }
        }
    }
}

@Composable
internal fun rememberHomeCoordinator(viewModel: HomeViewModel = koinViewModel()): HomeCoordinator = remember(viewModel) {
    HomeCoordinator(
        viewModel = viewModel,
    )
}
