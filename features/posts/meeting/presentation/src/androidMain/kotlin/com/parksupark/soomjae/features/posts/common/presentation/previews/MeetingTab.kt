package com.parksupark.soomjae.features.posts.common.presentation.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.features.posts.common.presentation.tab.MeetingTabScreen
import com.parksupark.soomjae.features.posts.common.presentation.tab.MeetingTabState

@Composable
@Preview(name = "MeetingTab")
private fun MeetingTabScreenPreview() {
    AppTheme {
        MeetingTabScreen(
            state = MeetingTabState(),
            onAction = { },
        )
    }
}
