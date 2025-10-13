package com.parksupark.soomjae.features.profile.presentation.profile.tabs.member_post

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.koin.compose.viewmodel.koinViewModel

class ProfileMemberPostCoordinator(
    val viewModel: ProfileMemberPostViewModel,
) {
    val screenStateFlow = viewModel.stateFlow

    fun handle(action: ProfileMemberPostAction) {
        when (action) {
            ProfileMemberPostAction.OnClick -> { // Handle action
            }
        }
    }
}

@Composable
fun rememberProfileMemberPostCoordinator(viewModel: ProfileMemberPostViewModel = koinViewModel()): ProfileMemberPostCoordinator =
    remember(viewModel) {
        ProfileMemberPostCoordinator(
            viewModel = viewModel,
        )
    }
