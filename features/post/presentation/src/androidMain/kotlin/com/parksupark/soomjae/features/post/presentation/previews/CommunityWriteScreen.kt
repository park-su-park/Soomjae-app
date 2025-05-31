package com.parksupark.soomjae.features.post.presentation.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.parksupark.soomjae.features.post.presentation.communitywrite.CommunityWriteScreen
import com.parksupark.soomjae.features.post.presentation.communitywrite.CommunityWriteState

@Composable
@Preview(name = "CommunityWrite")
private fun CommunityWriteScreenPreview() {
    CommunityWriteScreen(
        state = CommunityWriteState(),
        onAction = {},
        snackbarHost = {},
    )
}
