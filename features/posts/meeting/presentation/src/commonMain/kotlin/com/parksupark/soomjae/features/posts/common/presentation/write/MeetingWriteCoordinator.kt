package com.parksupark.soomjae.features.posts.common.presentation.write

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.parksupark.soomjae.features.posts.common.presentation.navigation.MeetingNavigator
import org.koin.compose.viewmodel.koinViewModel

class MeetingWriteCoordinator(
    private val navigator: MeetingNavigator,
    private val viewModel: MeetingWriteViewModel,
) {
    internal val screenStateFlow = viewModel.stateFlow

    internal fun handle(action: MeetingWriteAction) {
        when (action) {
            MeetingWriteAction.OnBackClick -> navigator.navigateBack()
        }
    }
}

@Composable
fun rememberMeetingWriteCoordinator(
    navigator: MeetingNavigator,
    viewModel: MeetingWriteViewModel = koinViewModel(),
): MeetingWriteCoordinator = remember(viewModel) {
    MeetingWriteCoordinator(
        navigator = navigator,
        viewModel = viewModel,
    )
}
