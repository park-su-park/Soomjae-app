package com.parksupark.soomjae.features.posts.meeting.presentation.meetingcreate

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.LocalDate

class MeetingCreateViewModel(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _stateFlow: MutableStateFlow<MeetingCreateState> = MutableStateFlow(MeetingCreateState())
    internal val stateFlow: StateFlow<MeetingCreateState> = _stateFlow.asStateFlow()

    fun createMeeting() {
        // TODO: Implement meeting creation logic
    }

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

    fun updateEndDate(newEndDate: LocalDate) {
        _stateFlow.update { state ->
            state.copy(
                meeting = state.meeting.copy(
                    endDate = newEndDate,
                ),
            )
        }
    }
}
