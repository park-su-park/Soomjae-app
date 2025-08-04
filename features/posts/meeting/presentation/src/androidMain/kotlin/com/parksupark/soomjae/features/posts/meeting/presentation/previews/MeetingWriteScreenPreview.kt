package com.parksupark.soomjae.features.posts.meeting.presentation.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.features.posts.meeting.presentation.write.MeetingWriteScreen
import com.parksupark.soomjae.features.posts.meeting.presentation.write.MeetingWriteState

@Composable
@Preview(name = "MeetingWrite")
private fun MeetingWriteScreenPreview() {
    AppTheme {
        MeetingWriteScreen(
            state = MeetingWriteState(),
            onAction = { },
        )
    }
}
