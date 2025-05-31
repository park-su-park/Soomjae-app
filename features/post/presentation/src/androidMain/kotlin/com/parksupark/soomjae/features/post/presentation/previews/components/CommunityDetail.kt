package com.parksupark.soomjae.features.post.presentation.previews.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.features.post.presentation.communitydetail.CommunityDetailScreen
import com.parksupark.soomjae.features.post.presentation.communitydetail.CommunityDetailState
import com.parksupark.soomjae.features.post.presentation.post.models.AuthorUi
import com.parksupark.soomjae.features.post.presentation.post.models.CommunityPostUi

@Composable
@Preview(name = "CommunityDetail")
private fun CommunityDetailScreenPreview() {
    AppTheme {
        CommunityDetailScreen(
            state = CommunityDetailState.Success(
                postDetail = CommunityPostUi(
                    id = "1",
                    title = "Sample Post Title",
                    content = "This is a sample post content for preview purposes.",
                    author = AuthorUi(
                        id = "author1",
                        nickname = "John Doe",
                        profileImageUrl = "https://example.com/profile.jpg",
                    ),
                    createdAt = "2023-10-01",
                ),
            ),
            onAction = {},
        )
    }
}
