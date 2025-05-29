package com.parksupark.soomjae.features.post.presentation.post.tabs.community

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.koin.compose.viewmodel.koinViewModel

class CommunityTabCoordinator(
    val viewModel: CommunityTabViewModel,
) {
    val screenStateFlow = viewModel.stateFlow

    fun handle(action: CommunityTabAction) {
        when (action) {
            CommunityTabAction.OnClick -> { // Handle action
            }
        }
    }
}

@Composable
fun rememberCommunityTabCoordinator(viewModel: CommunityTabViewModel = koinViewModel()): CommunityTabCoordinator = remember(viewModel) {
    CommunityTabCoordinator(
        viewModel = viewModel,
    )
}
