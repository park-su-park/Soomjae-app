package com.parksupark.soomjae.features.post.presentation.previews.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeSurface
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.features.post.presentation.post.models.AuthorUi
import com.parksupark.soomjae.features.post.presentation.post.models.CommunityPostUi
import com.parksupark.soomjae.features.post.presentation.post.tabs.community.components.CommunityPostCard
import kotlinx.datetime.Clock

@Preview
@Composable
private fun CommunityPostCardPreview() {
    AppTheme {
        SoomjaeSurface {
            CommunityPostCard(
                post = CommunityPostUi(
                    id = "1",
                    title = "Sample Post Title",
                    content = "This is a preview of the post content. It gives a brief overview of what the post is about.",
                    author = AuthorUi(
                        id = "1234",
                        nickname = "silentfox11",
                        profileImageUrl = "https://picsum.photos/200",
                    ),
                    createdAt = Clock.System.now().toString(),
                ),
                onFavoriteClick = {},
            )
        }
    }
}
