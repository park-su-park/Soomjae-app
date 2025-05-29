package com.parksupark.soomjae.features.post.presentation.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.features.post.presentation.post.tabs.meeting.MeetingTabScreen
import com.parksupark.soomjae.features.post.presentation.post.tabs.meeting.MeetingTabState

@Composable
@Preview(name = "MeetingTab")
private fun MeetingTabScreenPreview() {
    AppTheme {
        MeetingTabScreen(
            state = MeetingTabState(),
            onAction = {},
        )
    }
}
