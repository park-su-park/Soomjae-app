package com.parksupark.soomjae.features.posts.meeting.presentation.participant_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parksupark.soomjae.features.posts.common.domain.repositories.ParticipationRepository
import com.parksupark.soomjae.features.posts.meeting.presentation.participant_list.model.toParticipantUi
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ParticipantListViewModel(
    private val meetingId: Long,
    private val participationRepository: ParticipationRepository,
) : ViewModel() {
    private val _stateFlow: MutableStateFlow<ParticipantListState> =
        MutableStateFlow(ParticipantListState.Loading)
    val stateFlow: StateFlow<ParticipantListState> = _stateFlow.asStateFlow().onStart {
        loadParticipants()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = _stateFlow.value,
    )

    fun refreshParticipants() {
        viewModelScope.launch {
            loadParticipants()
        }
    }

    private suspend fun loadParticipants() {
        _stateFlow.update {
            ParticipantListState.Loading
        }

        participationRepository.getParticipants(meetingId = meetingId).fold(
            ifLeft = {
                _stateFlow.update {
                    ParticipantListState.Error
                }
            },
            ifRight = { participants ->
                _stateFlow.update {
                    ParticipantListState.Success(
                        participants = participants.map { participant ->
                            participant.toParticipantUi(currentUserId = "NONE") // TODO: Use current user ID from auth module
                        }.toImmutableList(),
                    )
                }
            },
        )
    }
}
