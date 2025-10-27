package com.parksupark.soomjae.features.posts.community.presentation.previews.tab

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import app.cash.paging.PagingData
import app.cash.paging.compose.collectAsLazyPagingItems
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.features.posts.community.presentation.models.CommunityPostUi
import com.parksupark.soomjae.features.posts.community.presentation.previews.providers.CommunityPostUiPreviewParameterProvider
import com.parksupark.soomjae.features.posts.community.presentation.tab.CommunityTabScreen
import com.parksupark.soomjae.features.posts.community.presentation.tab.post.CommunityTabPostState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.flowOf

@Composable
@Preview(name = "CommunityTab")
private fun CommunityTabScreenPreview(
    @PreviewParameter(CommunityPostUiPreviewParameterProvider::class) posts:
        ImmutableList<CommunityPostUi>,
) {
    AppTheme {
        CommunityTabScreen(
            state = CommunityTabPostState(),
            onAction = {},
            posts = flowOf(PagingData.from(posts)).collectAsLazyPagingItems(),
        )
    }
}
