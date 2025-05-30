package com.parksupark.soomjae.features.post.presentation.previews.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeSurface
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.features.post.domain.models.CommunityPost
import com.parksupark.soomjae.features.post.presentation.post.models.toUi
import com.parksupark.soomjae.features.post.presentation.post.tabs.community.components.CommunityPostCard

@Preview
@Composable
private fun CommunityPostCardPreview() {
    AppTheme {
        SoomjaeSurface {
            CommunityPostCard(
                post = CommunityPost(id = "1").toUi(),
                onFavoriteClick = {},
            )
        }
    }
}
