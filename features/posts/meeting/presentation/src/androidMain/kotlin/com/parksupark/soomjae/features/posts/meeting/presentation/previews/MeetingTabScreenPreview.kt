package com.parksupark.soomjae.features.posts.meeting.presentation.previews

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeSurface
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.core.presentation.ui.paging.lazyPagingItemsOf
import com.parksupark.soomjae.features.posts.meeting.presentation.previews.providers.MeetingPostUiPreviewParameterProvider
import com.parksupark.soomjae.features.posts.meeting.presentation.tab.MeetingTabScreen
import com.parksupark.soomjae.features.posts.meeting.presentation.tab.MeetingTabState
import com.parksupark.soomjae.features.posts.meeting.presentation.tab.models.MeetingPostUi
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
@Preview(name = "MeetingTab")
private fun MeetingTabScreenPreview(
    @PreviewParameter(
        MeetingPostUiPreviewParameterProvider::class,
        limit = 5,
    ) posts: ImmutableList<MeetingPostUi>,
) {
    AppTheme {
        SoomjaeSurface {
            MeetingTabScreen(
                state = MeetingTabState(),
                snackbarHostState = remember { SnackbarHostState() },
                onAction = { },
                posts = lazyPagingItemsOf(posts),
                createCache = persistentListOf(),
                listState = rememberLazyListState(),
            )
        }
    }
}
