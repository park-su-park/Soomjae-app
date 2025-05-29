package com.parksupark.soomjae.features.post.presentation.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.parksupark.soomjae.features.post.presentation.post.PostScreen
import com.parksupark.soomjae.features.post.presentation.post.PostState

@Composable
@Preview(name = "Post")
private fun PostScreenPreview() {
    PostScreen(
        state = PostState(),
        onAction = {},
    )
}
