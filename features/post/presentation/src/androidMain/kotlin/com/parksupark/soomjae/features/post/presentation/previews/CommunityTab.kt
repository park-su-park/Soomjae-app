package com.parksupark.soomjae.features.post.presentation.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.features.post.presentation.post.tabs.community.CommunityTabScreen
import com.parksupark.soomjae.features.post.presentation.post.tabs.community.CommunityTabState

@Composable
@Preview(name = "CommunityTab")
private fun CommunityTabScreenPreview() {
    AppTheme {
        CommunityTabScreen(
            state = CommunityTabState(),
            onAction = {},
        )
    }
}
