package com.parksupark.soomjae.features.posts.aggregate.presentation.previews.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeSurface
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.features.posts.aggregate.presentation.models.AuthorUi
import com.parksupark.soomjae.features.posts.community.presentation.models.CommunityPostUi
import com.parksupark.soomjae.features.posts.community.presentation.tab.components.CommunityPostCard
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Preview
@Composable
private fun CommunityPostCardPreview() {
    AppTheme {
        SoomjaeSurface {
            CommunityPostCard(
                post = CommunityPostUi(
                    id = 1,
                    title = "Sample Post Title",
                    content = "This is a preview of the post content. It gives a brief overview of what the post is about.",
                    author = AuthorUi(
                        id = "1234",
                        nickname = "silentfox11",
                        profileImageUrl = "https://picsum.photos/200",
                    ),
                    createdAt = Clock.System.now(),
                ),
                onFavoriteClick = {},
            )
        }
    }
}
