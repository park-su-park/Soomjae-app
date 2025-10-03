package com.parksupark.soomjae.features.posts.meeting.presentation.participant_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeCenterAlignedTopAppBar
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeCircularProgressIndicator
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeScaffold
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.core.presentation.ui.resources.value
import com.parksupark.soomjae.features.posts.meeting.presentation.participant_list.components.ParticipantListItem
import com.parksupark.soomjae.features.posts.meeting.presentation.participant_list.model.ParticipantUi
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.Res
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.participant_list_close_description
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.participant_list_title
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun ParticipantListScreen(
    state: ParticipantListState,
    onAction: (ParticipantListAction) -> Unit,
) {
    SoomjaeScaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            ParticipantListTopBar(
                onCloseClick = { onAction(ParticipantListAction.OnCloseClick) },
            )
        },
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            when (state) {
                is ParticipantListState.Loading -> ParticipantListLoadingContent()

                is ParticipantListState.Success -> ParticipantListSuccessScreen(
                    participants = state.participants,
                )

                is ParticipantListState.Error -> {
                    // TODO: Error Screen
                }
            }
        }
    }
}

@Composable
private fun ParticipantListSuccessScreen(participants: ImmutableList<ParticipantUi>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        itemsIndexed(
            items = participants,
            key = { _, item -> item.id },
        ) { index, participant ->
            ParticipantListItem(
                participant = participant,
                index = index + 1,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ParticipantListTopBar(onCloseClick: () -> Unit) {
    SoomjaeCenterAlignedTopAppBar(
        title = {
            Text(Res.string.participant_list_title.value)
        },
        actions = {
            IconButton(
                onClick = onCloseClick,
                content = {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = Res.string.participant_list_close_description.value,
                    )
                },
            )
        },
    )
}

@Composable
private fun ParticipantListLoadingContent() {
    SoomjaeCircularProgressIndicator()
}

@Composable
@Preview(name = "ParticipantList")
private fun ParticipantListScreenPreview() {
    AppTheme {
        ParticipantListScreen(
            state = ParticipantListState.Success(
                participants = persistentListOf(),
            ),
            onAction = { },
        )
    }
}
