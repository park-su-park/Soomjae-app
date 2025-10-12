package com.parksupark.soomjae.features.posts.meeting.presentation.write

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parksupark.soomjae.features.posts.meeting.presentation.navigation.MeetingNavigator
import com.parksupark.soomjae.features.posts.meeting.presentation.write.compose.MeetingComposeViewModel
import com.parksupark.soomjae.features.posts.meeting.presentation.write.create.MeetingCreateViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class MeetingWriteCoordinator(
    private val navigator: MeetingNavigator,
    val screenViewModel: MeetingWriteScreenViewModel,
    val composeViewModel: MeetingComposeViewModel,
    val createViewModel: MeetingCreateViewModel,
) : ViewModel() {
    internal val screenStateFlow = combine(
        screenViewModel.stateFlow,
        composeViewModel.stateFlow,
        createViewModel.stateFlow,
    ) { screenState, composeState, createState ->
        MeetingWriteCoordinatorState(
            screenState = screenState,
            composeState = composeState,
            createState = createState,
        )
    }.stateIn(
        scope = this.viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = MeetingWriteCoordinatorState(),
    )

    internal val events = composeViewModel.events

    internal fun handle(action: MeetingWriteAction) {
        when (action) {
            MeetingWriteAction.OnBackClick -> navigator.navigateBack()

            MeetingWriteAction.OnConfirmClick -> composeViewModel.submitPost()

            is MeetingWriteAction.OnCategorySelect -> composeViewModel.selectCategory(action.categoryId)

            is MeetingWriteAction.OnLocationSelect -> composeViewModel.selectLocation(action.locationCode)

            MeetingWriteAction.OnCreateMeetingClick -> screenViewModel.setScreenState(MeetingPostWriteScreenState.COMPOSE)
        }
    }
}
