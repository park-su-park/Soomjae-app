package com.parksupark.soomjae.features.posts.meeting.presentation.participant_list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.parksupark.soomjae.features.posts.meeting.presentation.navigation.MeetingNavigator
import org.koin.compose.viewmodel.koinViewModel

class ParticipantListCoordinator(
    val navigator: MeetingNavigator,
    val viewModel: ParticipantListViewModel,
) {
    val screenStateFlow = viewModel.stateFlow

    fun handle(action: ParticipantListAction) {
        when (action) {
            ParticipantListAction.OnCloseClick -> navigator.navigateBack()
        }
    }
}

@Composable
fun rememberParticipantListCoordinator(
    navigator: MeetingNavigator,
    viewModel: ParticipantListViewModel = koinViewModel(),
): ParticipantListCoordinator = remember(viewModel) {
    ParticipantListCoordinator(
        navigator = navigator,
        viewModel = viewModel,
    )
}
