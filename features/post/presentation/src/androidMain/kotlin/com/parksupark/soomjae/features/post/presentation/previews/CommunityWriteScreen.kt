package com.parksupark.soomjae.features.post.presentation.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.features.post.presentation.communitywrite.CommunityWriteScreen
import com.parksupark.soomjae.features.post.presentation.communitywrite.CommunityWriteState

@Composable
@Preview(name = "CommunityWrite")
private fun CommunityWriteScreenPreview() {
    AppTheme {
        CommunityWriteScreen(
            state = CommunityWriteState(),
            onAction = {},
            snackbarHost = {},
        )
    }
}
