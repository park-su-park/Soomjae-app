package com.parksupark.soomjae.features.posts.aggregate.presentation.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.features.posts.aggregate.presentation.post.PostScreen
import com.parksupark.soomjae.features.posts.aggregate.presentation.post.PostState

@Composable
@Preview(name = "Post")
private fun PostScreenPreview() {
    AppTheme {
        PostScreen(
            state = PostState(),
            onAction = {},
            bottomBar = {},
        )
    }
}
