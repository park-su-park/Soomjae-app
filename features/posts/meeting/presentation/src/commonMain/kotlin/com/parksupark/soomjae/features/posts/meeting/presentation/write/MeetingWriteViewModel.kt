package com.parksupark.soomjae.features.posts.meeting.presentation.write

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parksupark.soomjae.core.presentation.ui.utils.collectAsFlow
import com.parksupark.soomjae.features.posts.meeting.presentation.meetingcreate.MeetingCreateState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.atTime

class MeetingWriteViewModel : ViewModel() {
    private val _stateFlow: MutableStateFlow<MeetingWriteState> = MutableStateFlow(MeetingWriteState())
    internal val stateFlow: StateFlow<MeetingWriteState> = _stateFlow.asStateFlow()

    init {
        combine(
            _stateFlow.value.inputTitle.collectAsFlow(),
            _stateFlow.value.inputContent.collectAsFlow(),
            _stateFlow.distinctUntilChangedBy { it.meeting }.map { it.meeting },
        ) { (inputTitle, inputContent, meeting) ->
            val canSubmit = inputTitle.toString().trim().isNotBlank() &&
                inputContent.toString().trim().isNotBlank() &&
                meeting != null

            _stateFlow.update { it.copy(canSubmit = canSubmit) }
        }.launchIn(viewModelScope)
    }

    fun submitPost() {
        // TODO: Implement post submission logic
    }

    fun selectCategory(categoryId: Long) {
        _stateFlow.value = _stateFlow.value.copy(
            selectedCategory = _stateFlow.value.categories.find { it.id == categoryId },
        )
    }

    fun selectLocation(locationCode: Long) {
        _stateFlow.value = _stateFlow.value.copy(
            selectedLocation = _stateFlow.value.locations.find { it.code == locationCode },
        )
    }

    // <editor-fold desc="MeetingCreate">
    private val _createStateFlow = MutableStateFlow(MeetingCreateState())
    internal val createStateFlow = _createStateFlow.asStateFlow()

    fun createMeeting() {
        _stateFlow.update { writeState ->
            writeState.copy(meeting = createStateFlow.value.meeting)
        }
    }

    fun updateStartDate(newStartDate: LocalDate) {
        _createStateFlow.update { state ->
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
        _createStateFlow.update { state ->
            state.copy(
                meeting = state.meeting.copy(
                    startTime = newStartTime,
                ),
            )
        }
    }

    fun updateEndDate(newEndDate: LocalDate) {
        _createStateFlow.update { state ->
            state.copy(
                meeting = state.meeting.copy(
                    endDate = newEndDate,
                ),
            )
        }
    }

    fun updateEndTime(newEndTime: LocalTime) {
        _createStateFlow.update { state ->
            val startDateTime = state.meeting.startDate.atTime(state.meeting.startTime)
            val endDateTime = state.meeting.endDate?.atTime(newEndTime)

            val newStartTime = if (endDateTime != null && endDateTime > startDateTime) {
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
    // </editor-fold>
}

private fun LocalTime.minusHours(hours: Int): LocalTime = LocalTime(
    hour = (this.hour - hours) % 24,
    minute = this.minute,
    second = this.second,
    nanosecond = this.nanosecond,
)
