package com.parksupark.soomjae.features.posts.community.presentation.tab

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.cash.paging.LoadStateError
import app.cash.paging.LoadStateLoading
import app.cash.paging.LoadStateNotLoading
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.itemKey
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeCircularProgressIndicator
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeHorizontalDivider
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaePullToRefreshBox
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeScaffold
import com.parksupark.soomjae.features.posts.common.presentation.components.WritePostFab
import com.parksupark.soomjae.features.posts.community.presentation.models.CommunityPostUi
import com.parksupark.soomjae.features.posts.community.presentation.tab.components.CommunityPostCard
import com.parksupark.soomjae.features.posts.community.presentation.tab.post.CommunityTabPostAction
import com.parksupark.soomjae.features.posts.community.presentation.tab.post.CommunityTabPostState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CommunityTabScreen(
    state: CommunityTabPostState,
    onAction: (CommunityTabPostAction) -> Unit,
    posts: LazyPagingItems<CommunityPostUi>,
) {
    LaunchedEffect(posts.loadState.refresh, onAction) {
        val refresh = posts.loadState.refresh
        if (refresh is LoadStateNotLoading && state.isPostsRefreshing) {
            onAction(CommunityTabPostAction.OnRefreshChange(false))
        }
    }

    SoomjaeScaffold(modifier = Modifier.fillMaxSize()) { _ ->
        val isRefreshing = state.isPostsRefreshing && posts.loadState.refresh is LoadStateLoading

        SoomjaePullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = { onAction(CommunityTabPostAction.OnPullToRefresh) },
            modifier = Modifier.fillMaxSize(),
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                val refresh = posts.loadState.refresh
                if (refresh is LoadStateLoading && !isRefreshing) {
                    item {
                        SoomjaeCircularProgressIndicator()
                    }
                } else if (refresh is LoadStateError && !isRefreshing) {
                    item {
                        Text(refresh.error.message!!)
                    }
                } else {
                    items(count = posts.itemCount, key = posts.itemKey { it.id }) { index ->
                        posts[index]?.let { post ->
                            CommunityPostCard(
                                post = post,
                                modifier = Modifier.fillMaxWidth()
                                    .clickable {
                                        onAction(CommunityTabPostAction.OnPostClick(post.id))
                                    },
                            )
                            SoomjaeHorizontalDivider()
                        }
                    }
                }
            }

            WritePostFab(
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 8.dp),
                onClick = { onAction(CommunityTabPostAction.OnCommunityWriteClick) },
            )
        }
    }
}
