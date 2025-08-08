package com.parksupark.soomjae.features.posts.meeting.presentation.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.parksupark.soomjae.features.posts.meeting.presentation.navigation.MeetingNavigator
import org.koin.compose.viewmodel.koinViewModel

class MeetingDetailCoordinator(
    private val navigator: MeetingNavigator,
    val viewModel: MeetingDetailViewModel,
) {
    val screenStateFlow = viewModel.stateFlow

    fun handle(action: MeetingDetailAction) {
        when (action) {
            MeetingDetailAction.OnBackClick -> navigator.navigateBack()
        }
    }
}

@Composable
fun rememberMeetingDetailCoordinator(
    navigator: MeetingNavigator,
    viewModel: MeetingDetailViewModel = koinViewModel(),
): MeetingDetailCoordinator = remember(viewModel) {
    MeetingDetailCoordinator(
        navigator = navigator,
        viewModel = viewModel,
    )
}
