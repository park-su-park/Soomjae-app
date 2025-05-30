package com.parksupark.soomjae.features.post.presentation.post.tabs.community

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.koin.compose.viewmodel.koinViewModel

internal class CommunityTabCoordinator(
    viewModel: CommunityTabViewModel,
) {
    val screenStateFlow = viewModel.stateFlow

    fun handle(action: CommunityTabAction) {
        when (action) {
            is CommunityTabAction.OnClick -> {
                // TODO
            }

            is CommunityTabAction.OnFavoriteClick -> {
                // TODO
            }

            is CommunityTabAction.OnPostClick -> {
                // TODO
            }
        }
    }
}

@Composable
internal fun rememberCommunityTabCoordinator(viewModel: CommunityTabViewModel = koinViewModel()): CommunityTabCoordinator =
    remember(viewModel) {
        CommunityTabCoordinator(
            viewModel = viewModel,
        )
    }
