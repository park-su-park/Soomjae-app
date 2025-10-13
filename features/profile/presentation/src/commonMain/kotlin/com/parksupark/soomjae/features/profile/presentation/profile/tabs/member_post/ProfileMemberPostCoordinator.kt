package com.parksupark.soomjae.features.profile.presentation.profile.tabs.member_post

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

class ProfileMemberPostCoordinator(
    val viewModel: ProfileMemberPostViewModel,
) {
    val screenStateFlow = viewModel.stateFlow
    val posts = viewModel.posts

    val events = viewModel.events

    fun handle(action: ProfileMemberPostAction) {
        when (action) {
            ProfileMemberPostAction.OnClick -> { // Handle action
            }

            ProfileMemberPostAction.OnPullToRefresh -> viewModel.refreshPosts()
            is ProfileMemberPostAction.RefreshChange -> viewModel.setRefreshing(action.isRefreshing)
        }
    }
}

@Composable
fun rememberProfileMemberPostCoordinator(
    userId: Long,
    viewModel: ProfileMemberPostViewModel = koinViewModel(
        key = "profile_member_post_view_model_$userId",
        parameters = { parametersOf(userId) },
    ),
): ProfileMemberPostCoordinator = remember(userId, viewModel) {
    ProfileMemberPostCoordinator(
        viewModel = viewModel,
    )
}
