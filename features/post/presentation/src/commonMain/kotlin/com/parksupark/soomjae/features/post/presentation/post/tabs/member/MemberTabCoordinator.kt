package com.parksupark.soomjae.features.post.presentation.post.tabs.member

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.koin.compose.viewmodel.koinViewModel

class MemberTabCoordinator(
    val viewModel: MemberTabViewModel,
) {
    val screenStateFlow = viewModel.stateFlow

    fun handle(action: MemberTabAction) {
        when (action) {
            MemberTabAction.OnClick -> { // Handle action
            }
        }
    }
}

@Composable
fun rememberMemberTabCoordinator(viewModel: MemberTabViewModel = koinViewModel()): MemberTabCoordinator = remember(viewModel) {
    MemberTabCoordinator(
        viewModel = viewModel,
    )
}
