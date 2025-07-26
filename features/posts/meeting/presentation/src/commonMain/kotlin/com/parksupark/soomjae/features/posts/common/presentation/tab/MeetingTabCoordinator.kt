package com.parksupark.soomjae.features.posts.common.presentation.tab

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.koin.compose.viewmodel.koinViewModel

class MeetingTabCoordinator(
    val viewModel: MeetingTabViewModel,
) {
    val screenStateFlow = viewModel.stateFlow

    fun handle(action: MeetingTabAction) {
        when (action) {
            MeetingTabAction.OnClick -> { // Handle action
            }
        }
    }
}

@Composable
fun rememberMeetingTabCoordinator(viewModel: MeetingTabViewModel = koinViewModel()): MeetingTabCoordinator = remember(viewModel) {
    MeetingTabCoordinator(
        viewModel = viewModel,
    )
}
