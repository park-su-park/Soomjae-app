package com.parksupark.soomjae.features.post.presentation.previews.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.features.post.presentation.communitydetail.CommunityDetailScreen
import com.parksupark.soomjae.features.post.presentation.communitydetail.CommunityDetailState

@Composable
@Preview(name = "CommunityDetail")
private fun CommunityDetailScreenPreview() {
    AppTheme {
        CommunityDetailScreen(
            state = CommunityDetailState(),
            onAction = {},
        )
    }
}
