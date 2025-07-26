package com.parksupark.soomjae.features.posts.community.presentation.tab

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import app.cash.paging.LoadStateError
import app.cash.paging.LoadStateLoading
import app.cash.paging.LoadStateNotLoading
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.itemKey
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeCircularProgressIndicator
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeFab
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeHorizontalDivider
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaePullToRefreshBox
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeScaffold
import com.parksupark.soomjae.core.presentation.ui.resources.value
import com.parksupark.soomjae.features.posts.community.presentation.models.CommunityPostUi
import com.parksupark.soomjae.features.posts.community.presentation.resources.Res
import com.parksupark.soomjae.features.posts.community.presentation.resources.post_community_fab_description
import com.parksupark.soomjae.features.posts.community.presentation.tab.components.CommunityPostCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CommunityTabScreen(
    state: CommunityTabState,
    onAction: (CommunityTabAction) -> Unit,
    posts: LazyPagingItems<CommunityPostUi>,
) {
    val onAction by rememberUpdatedState(onAction)

    LaunchedEffect(posts.loadState.refresh) {
        val refresh = posts.loadState.refresh
        if (refresh is LoadStateNotLoading && state.isPostsRefreshing) {
            onAction(CommunityTabAction.OnRefreshChange(false))
        }
    }

    SoomjaeScaffold(
        floatingActionButton = {
            SoomjaeFab(
                onClick = { onAction(CommunityTabAction.OnCommunityWriteClick) },
                content = { Icon(Icons.Outlined.Edit, contentDescription = Res.string.post_community_fab_description.value) },
            )
        },
    ) { innerPadding ->
        val isRefreshing = state.isPostsRefreshing && posts.loadState.refresh is LoadStateLoading

        SoomjaePullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = { onAction(CommunityTabAction.OnPullToRefresh) },
            modifier = Modifier.fillMaxSize().padding(innerPadding),
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
                        val post = posts[index]
                        if (post != null) {
                            CommunityPostCard(
                                post = post,
                                onFavoriteClick = { onAction(CommunityTabAction.OnFavoriteClick(post.id)) },
                                modifier = Modifier.fillMaxWidth()
                                    .clickable {
                                        onAction(CommunityTabAction.OnPostClick(post.id))
                                    },
                            )
                            SoomjaeHorizontalDivider()
                        }
                    }
                }
            }
        }
    }
}
