package com.parksupark.soomjae.features.post.presentation.communitydetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.koin.compose.viewmodel.koinViewModel

internal class CommunityDetailCoordinator(
    val viewModel: CommunityDetailViewModel,
) {
    val screenStateFlow = viewModel.uiStateFlow

    fun handle(action: CommunityDetailAction) {
        when (action) {
            CommunityDetailAction.OnClick -> { // Handle action
            }
        }
    }
}

@Composable
internal fun rememberCommunityDetailCoordinator(viewModel: CommunityDetailViewModel = koinViewModel()): CommunityDetailCoordinator =
    remember(viewModel) {
        CommunityDetailCoordinator(
            viewModel = viewModel,
        )
    }
