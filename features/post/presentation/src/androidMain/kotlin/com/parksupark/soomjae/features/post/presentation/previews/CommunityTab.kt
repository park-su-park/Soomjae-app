package com.parksupark.soomjae.features.post.presentation.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import app.cash.paging.PagingData
import app.cash.paging.compose.collectAsLazyPagingItems
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.features.post.domain.models.CommunityPost
import com.parksupark.soomjae.features.post.presentation.models.toUi
import com.parksupark.soomjae.features.post.presentation.post.tabs.community.CommunityTabScreen
import com.parksupark.soomjae.features.post.presentation.post.tabs.community.CommunityTabState
import com.parksupark.soomjae.features.post.presentation.previews.proviers.CommunityPostPreviewParameterProvider
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.flowOf

@Composable
@Preview(name = "CommunityTab")
private fun CommunityTabScreenPreview(
    @PreviewParameter(CommunityPostPreviewParameterProvider::class) posts: ImmutableList<CommunityPost>,
) {
    AppTheme {
        CommunityTabScreen(
            state = CommunityTabState(),
            onAction = {},
            posts = flowOf(PagingData.from(posts.map { it.toUi() })).collectAsLazyPagingItems(),
        )
    }
}
