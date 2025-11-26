package com.parksupark.soomjae.features.posts.member.presentation.post_list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaePullToRefreshBox
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeScaffold
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.core.presentation.ui.components.SoomjaeSnackbarHost
import com.parksupark.soomjae.core.presentation.ui.paging.emptyLazyPagingItems
import com.parksupark.soomjae.features.posts.common.presentation.components.WritePostFab
import com.parksupark.soomjae.features.posts.member.presentation.post_list.components.MemberPostListItem
import com.parksupark.soomjae.features.posts.member.presentation.post_list.models.MemberPostUi
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MemberPostListTab(
    state: MemberPostListState,
    onAction: (MemberPostListAction) -> Unit,
    snackbarHostState: SnackbarHostState,
    posts: LazyPagingItems<MemberPostUi>,
) {
    RefreshEffect(
        posts = posts,
        isRefreshing = state.isPostsRefreshing,
        onRefreshChange = { onAction(MemberPostListAction.OnRefreshChange(it)) },
    )

    SoomjaeScaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SoomjaeSnackbarHost(hostState = snackbarHostState)
        },
    ) {
        SoomjaePullToRefreshBox(
            isRefreshing = state.isPostsRefreshing,
            onRefresh = { onAction(MemberPostListAction.OnPullToRefresh) },
            modifier = Modifier.fillMaxSize(),
        ) {
            MemberPostList(posts = posts, onAction = onAction)

            WriteMeetingPostFab(
                onWriteClick = { onAction(MemberPostListAction.OnWritePostClick) },
                modifier = Modifier.align(Alignment.BottomCenter),
            )
        }
    }
}

@Composable
private fun RefreshEffect(
    posts: LazyPagingItems<MemberPostUi>,
    isRefreshing: Boolean,
    onRefreshChange: (Boolean) -> Unit,
) {
    LaunchedEffect(posts.loadState.refresh, onRefreshChange) {
        val refresh = posts.loadState.refresh
        if (refresh is LoadState.NotLoading && isRefreshing) {
            onRefreshChange(false)
        }
    }
}

@Composable
private fun MemberPostList(
    posts: LazyPagingItems<MemberPostUi>,
    onAction: (MemberPostListAction) -> Unit,
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(
            count = posts.itemCount,
            key = posts.itemKey { it.id },
        ) { index ->
            val post = posts[index] ?: return@items
            MemberPostListItem(
                post = post,
                onMenuClick = {
                    // TODO
                },
                onCommentClick = {
                    onAction(MemberPostListAction.OnCommentClick(postId = post.id))
                },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun WriteMeetingPostFab(
    onWriteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    WritePostFab(onClick = onWriteClick, modifier = modifier.padding(8.dp))
}

@Composable
@Preview(name = "MemberPostList")
private fun MemberPostListScreenPreview() {
    AppTheme {
        MemberPostListTab(
            state = MemberPostListState(),
            onAction = { },
            posts = emptyLazyPagingItems(),
            snackbarHostState = remember { SnackbarHostState() },
        )
    }
}
