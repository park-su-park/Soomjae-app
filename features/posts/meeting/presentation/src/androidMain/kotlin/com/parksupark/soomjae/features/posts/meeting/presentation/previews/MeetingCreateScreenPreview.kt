package com.parksupark.soomjae.features.posts.meeting.presentation.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.features.posts.meeting.presentation.meetingcreate.MeetingCreateScreen
import com.parksupark.soomjae.features.posts.meeting.presentation.meetingcreate.MeetingCreateState

@Composable
@Preview(name = "MeetingCreate")
private fun MeetingCreateScreenPreview() {
    AppTheme {
        MeetingCreateScreen(
            state = MeetingCreateState(),
            onAction = {},
        )
    }
}
