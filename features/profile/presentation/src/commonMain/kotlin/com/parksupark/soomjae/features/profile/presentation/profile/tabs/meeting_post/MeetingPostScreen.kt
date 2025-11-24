package com.parksupark.soomjae.features.profile.presentation.profile.tabs.meeting_post

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaePullToRefreshBox
import com.parksupark.soomjae.core.presentation.ui.components.RefreshEffect
import com.parksupark.soomjae.core.presentation.ui.paging.emptyLazyPagingItems
import com.parksupark.soomjae.core.presentation.ui.post.components.MeetingPostCard
import com.parksupark.soomjae.core.presentation.ui.post.model.MeetingPostUi
import com.parksupark.soomjae.core.presentation.ui.screens.EmptyContent
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun ProfileMeetingPostScreen(
    state: MeetingPostState,
    onAction: (MeetingPostAction) -> Unit,
    listState: LazyListState,
    posts: LazyPagingItems<MeetingPostUi>,
) {
    val isRefreshing = state.isRefreshing

    RefreshEffect(
        refreshState = posts.loadState.refresh,
        isRefreshing = isRefreshing,
        onRefreshChange = { refreshing ->
            onAction(MeetingPostAction.RefreshChange(isRefreshing = refreshing))
        },
    )

    SoomjaePullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = { onAction(MeetingPostAction.OnPullToRefresh) },
        modifier = Modifier.fillMaxSize(),
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = listState,
            contentPadding = PaddingValues(vertical = 12.dp),
        ) {
            if (posts.itemCount == 0) {
                item {
                    EmptyContent(modifier = Modifier.fillMaxWidth())
                }
            }

            items(count = posts.itemCount, key = posts.itemKey { it.id }) { index ->
                val post = posts[index] ?: return@items

                MeetingPostCard(
                    post = post,
                    onFavoriteClick = { },
                    modifier = Modifier.fillMaxWidth()
                        .clickable {
                            onAction(MeetingPostAction.OnClickPost(post.id))
                        },
                )
            }
        }
    }
}

@Composable
@Preview
private fun MeetingPostScreenPreview() {
    ProfileMeetingPostScreen(
        state = MeetingPostState(),
        onAction = { },
        listState = rememberLazyListState(),
        posts = emptyLazyPagingItems(),
    )
}
