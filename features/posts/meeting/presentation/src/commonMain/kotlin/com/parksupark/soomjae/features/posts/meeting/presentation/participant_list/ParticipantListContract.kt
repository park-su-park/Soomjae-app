package com.parksupark.soomjae.features.posts.meeting.presentation.participant_list

import com.parksupark.soomjae.features.posts.meeting.presentation.participant_list.model.ParticipantUi
import kotlinx.collections.immutable.ImmutableList

sealed class ParticipantListState {
    internal data object Loading : ParticipantListState()

    internal data class Success(
        val participants: ImmutableList<ParticipantUi>,
    ) : ParticipantListState()

    internal data object Error : ParticipantListState()
}

sealed interface ParticipantListAction {
    data object OnCloseClick : ParticipantListAction
}
