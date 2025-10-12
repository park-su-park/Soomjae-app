package com.parksupark.soomjae.features.posts.meeting.presentation.write

import com.parksupark.soomjae.core.presentation.ui.utils.UiText
import com.parksupark.soomjae.features.posts.meeting.presentation.write.compose.MeetingComposeState
import com.parksupark.soomjae.features.posts.meeting.presentation.write.create.MeetingCreateState

data class MeetingWriteCoordinatorState(
    val screenState: MeetingWriteScreenState = MeetingWriteScreenState(),
    val composeState: MeetingComposeState = MeetingComposeState(),
    val createState: MeetingCreateState = MeetingCreateState(),
)

data class MeetingWriteScreenState(
    val screenState: MeetingPostWriteScreenState = MeetingPostWriteScreenState.CREATE,
)

enum class MeetingPostWriteScreenState {
    CREATE,
    COMPOSE,
}

internal sealed interface MeetingWriteAction {
    data object OnBackClick : MeetingWriteAction

    data object OnConfirmClick : MeetingWriteAction

    data object OnCreateMeetingClick : MeetingWriteAction

    data class OnCategorySelect(val categoryId: Long) : MeetingWriteAction

    data class OnLocationSelect(val locationCode: Long) : MeetingWriteAction
}

internal sealed interface MeetingWriteEvent {
    data class OnPostCreateSuccess(val postId: Long) : MeetingWriteEvent

    data class OnPostCreateFailure(val error: UiText) : MeetingWriteEvent
}
