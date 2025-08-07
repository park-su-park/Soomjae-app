package com.parksupark.soomjae.features.posts.meeting.presentation.write

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.parksupark.soomjae.features.posts.meeting.presentation.navigation.MeetingNavigator
import org.koin.compose.viewmodel.koinViewModel

class MeetingWriteCoordinator(
    private val navigator: MeetingNavigator,
    val viewModel: MeetingWriteViewModel,
) {
    internal val screenStateFlow = viewModel.stateFlow

    internal fun handle(action: MeetingWriteAction) {
        when (action) {
            MeetingWriteAction.OnBackClick -> navigator.navigateBack()

            MeetingWriteAction.OnConfirmClick -> viewModel.submitPost()

            MeetingWriteAction.OnCreateMeetingClick -> navigator.navigateToMeetingCreate()

            is MeetingWriteAction.OnCategorySelect -> viewModel.selectCategory(action.categoryId)

            is MeetingWriteAction.OnLocationSelect -> viewModel.selectLocation(action.locationCode)
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
