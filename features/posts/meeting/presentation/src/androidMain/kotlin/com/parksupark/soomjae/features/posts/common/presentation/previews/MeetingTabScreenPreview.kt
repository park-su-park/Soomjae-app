package com.parksupark.soomjae.features.posts.common.presentation.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import app.cash.paging.PagingData
import app.cash.paging.compose.collectAsLazyPagingItems
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.features.posts.common.presentation.previews.providers.MeetingPostUiPreviewParameterProvider
import com.parksupark.soomjae.features.posts.common.presentation.tab.MeetingTabScreen
import com.parksupark.soomjae.features.posts.common.presentation.tab.MeetingTabState
import com.parksupark.soomjae.features.posts.common.presentation.tab.models.MeetingPostUi
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
@Preview(name = "MeetingTab")
private fun MeetingTabScreenPreview(
    @PreviewParameter(MeetingPostUiPreviewParameterProvider::class, limit = 5) posts: ImmutableList<MeetingPostUi>,
) {
    AppTheme {
        MeetingTabScreen(
            state = MeetingTabState(),
            onAction = { },
            posts = MutableStateFlow(PagingData.from(posts)).collectAsLazyPagingItems(),
        )
    }
}
