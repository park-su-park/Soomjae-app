package com.parksupark.soomjae.features.posts.meeting.presentation.write

import com.parksupark.soomjae.core.presentation.ui.utils.UiText
import com.parksupark.soomjae.features.posts.meeting.presentation.models.DateTimeRangeUi
import com.parksupark.soomjae.features.posts.meeting.presentation.write.post_content.MeetingPostContentState

data class MeetingPostWriteCoordinatorState(
    val contentState: MeetingPostContentState = MeetingPostContentState(),
)

sealed interface MeetingPostWriteAction {
    data object OnBackClick : MeetingPostWriteAction

    data object OnSubmitClick : MeetingPostWriteAction

    data class OnCategorySelect(val categoryId: Long) : MeetingPostWriteAction

    data class OnLocationSelect(val locationCode: Long) : MeetingPostWriteAction

    data class OnParticipantLimitToggled(val isUnlimited: Boolean) : MeetingPostWriteAction

    data class OnAllDayToggled(val isAllDay: Boolean) : MeetingPostWriteAction

    data class OnMeetingPeriodChanged(
        val period: DateTimeRangeUi,
        val changedField: PeriodField,
    ) : MeetingPostWriteAction

    enum class PeriodField { StartDate, StartTime, EndDate, EndTime, All }
}

internal sealed interface MeetingPostWriteEvent {
    data class OnPostCreateSuccess(val postId: Long) : MeetingPostWriteEvent

    data class ShowErrorToast(val error: UiText) : MeetingPostWriteEvent
}
