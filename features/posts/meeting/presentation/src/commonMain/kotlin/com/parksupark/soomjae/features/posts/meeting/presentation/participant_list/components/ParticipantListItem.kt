package com.parksupark.soomjae.features.posts.meeting.presentation.participant_list.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeListItem
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeSurface
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.core.presentation.ui.components.ProfileImage
import com.parksupark.soomjae.core.presentation.ui.resources.value
import com.parksupark.soomjae.features.posts.meeting.presentation.participant_list.model.ParticipantUi
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.Res
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.participant_list_item_nth_participant
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun ParticipantListItem(
    participant: ParticipantUi,
    index: Int,
    modifier: Modifier = Modifier,
) {
    SoomjaeListItem(
        overlineContent = {
            Text(
                text = participant.nickname,
                style = SoomjaeTheme.typography.labelL,
            )
        },
        headlineContent = {
            Text(
                text = Res.string.participant_list_item_nth_participant.value(index),
                style = SoomjaeTheme.typography.body2,
                color = SoomjaeTheme.colorScheme.text3,
            )
        },
        modifier = modifier,
        leadingContent = {
            ProfileImage(
                imageUrl = participant.profileImageUrl,
                size = 48.dp,
            )
        },
    )
}

@Preview
@Composable
private fun ParticipantListItemPreview() {
    AppTheme {
        SoomjaeSurface {
            ParticipantListItem(
                participant = ParticipantUi(
                    id = "1",
                    nickname = "nickname",
                    profileImageUrl = "https://picsum.photos/200",
                    isCurrentUser = false,
                ),
                index = 1,
            )
        }
    }
}
