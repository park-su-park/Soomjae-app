package com.parksupark.soomjae.features.posts.meeting.presentation.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.features.posts.meeting.presentation.detail.MeetingDetailScreen
import com.parksupark.soomjae.features.posts.meeting.presentation.detail.MeetingDetailState

@Composable
@Preview
private fun MeetingDetailScreenPreview() {
    AppTheme {
        MeetingDetailScreen(
            state = MeetingDetailState(),
            onAction = { },
        )
    }
}
