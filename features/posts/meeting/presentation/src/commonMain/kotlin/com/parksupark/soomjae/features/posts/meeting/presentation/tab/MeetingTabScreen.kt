package com.parksupark.soomjae.features.posts.meeting.presentation.tab

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.itemKey
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeHorizontalDivider
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaePullToRefreshBox
import com.parksupark.soomjae.features.posts.common.presentation.components.WritePostFab
import com.parksupark.soomjae.features.posts.meeting.presentation.tab.components.MeetingPostCard
import com.parksupark.soomjae.features.posts.meeting.presentation.tab.models.MeetingPostUi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MeetingTabScreen(
    state: MeetingTabState,
    onAction: (MeetingTabAction) -> Unit,
    posts: LazyPagingItems<MeetingPostUi>,
) {
    SoomjaePullToRefreshBox(
        isRefreshing = posts.loadState.refresh is LoadState.Loading,
        onRefresh = { posts.refresh() },
        modifier = Modifier.fillMaxSize(),
        state = rememberPullToRefreshState(),
    ) {
        LazyColumn {
            items(count = posts.itemCount, key = posts.itemKey { it.id }) { index ->
                posts[index]?.let { post ->
                    MeetingPostCard(
                        post = post,
                        onFavoriteClick = { onAction(MeetingTabAction.OnPostLikeClick(post.id)) },
                        modifier = Modifier.fillMaxSize()
                            .clickable {
                                onAction(MeetingTabAction.OnPostClick(post.id))
                            },
                    )
                    SoomjaeHorizontalDivider()
                }
            }
        }

        WritePostFab(
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 8.dp),
            onClick = { onAction(MeetingTabAction.OnWritePostClick) },
        )
    }
}
