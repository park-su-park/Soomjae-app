package com.parksupark.soomjae.features.post.presentation.post.tabs.community

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.cash.paging.LoadStateError
import app.cash.paging.LoadStateLoading
import app.cash.paging.LoadStateNotLoading
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.itemKey
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeCircularProgressIndicator
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeFab
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeScaffold
import com.parksupark.soomjae.core.presentation.ui.resources.value
import com.parksupark.soomjae.features.post.presentation.post.PostAction
import com.parksupark.soomjae.features.post.presentation.post.models.CommunityPostUi
import com.parksupark.soomjae.features.post.presentation.post.tabs.community.components.CommunityPostCard
import com.parksupark.soomjae.features.post.presentation.resources.Res
import com.parksupark.soomjae.features.post.presentation.resources.post_community_fab_description

@Composable
internal fun CommunityTabScreen(
    state: CommunityTabState,
    onPostAction: (PostAction) -> Unit,
    onAction: (CommunityTabAction) -> Unit,
    posts: LazyPagingItems<CommunityPostUi>,
) {
    SoomjaeScaffold(
        floatingActionButton = {
            SoomjaeFab(
                onClick = { onPostAction(PostAction.OnCommunityWriteClick) },
                content = { Icon(Icons.Outlined.Edit, contentDescription = Res.string.post_community_fab_description.value) },
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = innerPadding,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            when (val loadState = posts.loadState.refresh) {
                LoadStateLoading -> item { SoomjaeCircularProgressIndicator() }

                is LoadStateNotLoading -> items(count = posts.itemCount, key = posts.itemKey { it.id }) { index ->
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
                    }
                }

                is LoadStateError -> item {
                    Text(loadState.error.message!!)
                }

                else -> error("when should be exhaustive")
            }
        }
    }
}
