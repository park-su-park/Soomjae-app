package com.parksupark.soomjae.features.posts.meeting.presentation.participant_list

import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun ParticipantListScreen(
    state: ParticipantListState,
    onAction: (ParticipantListAction) -> Unit,
) {
    // TODO UI Rendering
}

@Composable
@Preview(name = "ParticipantList")
private fun ParticipantListScreenPreview() {
    ParticipantListScreen(
        state = ParticipantListState.Success(
            participants = listOf(),
        ),
        onAction = { },
    )
}
