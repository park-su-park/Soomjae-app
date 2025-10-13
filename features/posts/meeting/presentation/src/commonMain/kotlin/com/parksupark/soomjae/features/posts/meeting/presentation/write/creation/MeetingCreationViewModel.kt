package com.parksupark.soomjae.features.posts.meeting.presentation.write.creation

import androidx.lifecycle.ViewModel
import com.parksupark.soomjae.features.posts.meeting.presentation.models.MeetingCreationUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.atTime

class MeetingCreationViewModel : ViewModel() {
    private val _stateFlow: MutableStateFlow<MeetingCreationState> = MutableStateFlow(MeetingCreationState())
    val stateFlow: StateFlow<MeetingCreationState> = _stateFlow.asStateFlow()

    fun createMeeting(): MeetingCreationUi = stateFlow.value.meeting

    fun updateStartDate(newStartDate: LocalDate) {
        _stateFlow.update { state ->
            // update end date if it is before the new start date
            val newEndDate = state.meeting.endDate?.let { currentEndDate ->
                if (newStartDate > currentEndDate) newStartDate else currentEndDate
            } ?: state.meeting.endDate

            state.copy(
                meeting = state.meeting.copy(
                    startDate = newStartDate,
                    endDate = newEndDate,
                ),
            )
        }
    }

    fun updateStartTime(newStartTime: LocalTime) {
        _stateFlow.update { state ->
            state.copy(
                meeting = state.meeting.copy(
                    startTime = newStartTime,
                ),
            )
        }
    }

    fun updateEndDate(newEndDate: LocalDate) {
        _stateFlow.update { state ->
            state.copy(
                meeting = state.meeting.copy(
                    endDate = newEndDate,
                ),
            )
        }
    }

    fun updateEndTime(newEndTime: LocalTime) {
        _stateFlow.update { state ->
            val startDateTime = state.meeting.startDate.atTime(state.meeting.startTime)
            val endDateTime = state.meeting.endDate?.atTime(newEndTime)

            val newStartTime = if (endDateTime != null && endDateTime < startDateTime) {
                state.meeting.startTime.minusHours(1)
            } else {
                state.meeting.startTime
            }

            state.copy(
                meeting = state.meeting.copy(
                    startTime = newStartTime,
                    endTime = newEndTime,
                ),
            )
        }
    }
}

private fun LocalTime.minusHours(hours: Int): LocalTime = LocalTime(
    hour = ((this.hour - hours) % 24 + 24) % 24,
    minute = this.minute,
    second = this.second,
    nanosecond = this.nanosecond,
)
