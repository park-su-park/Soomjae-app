package com.parksupark.soomjae.features.posts.community.presentation.previews.tab.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeSurface
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.features.posts.community.presentation.models.CommunityPostUi
import com.parksupark.soomjae.features.posts.community.presentation.previews.providers.CommunityPostUiPreviewParameterProvider
import com.parksupark.soomjae.features.posts.community.presentation.tab.components.CommunityPostCard
import kotlin.time.ExperimentalTime
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalTime::class)
@Preview
@Composable
private fun CommunityPostCardPreview(
    @PreviewParameter(CommunityPostUiPreviewParameterProvider::class) posts: ImmutableList<CommunityPostUi>,
) {
    AppTheme {
        SoomjaeSurface {
            CommunityPostCard(
                post = posts[0],
                onFavoriteClick = { },
                canLike = true,
            )
        }
    }
}
