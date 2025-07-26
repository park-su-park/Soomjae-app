package com.parksupark.soomjae.features.posts.community.presentation.previews.write

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.features.posts.community.presentation.write.CommunityWriteScreen
import com.parksupark.soomjae.features.posts.community.presentation.write.CommunityWriteState

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
