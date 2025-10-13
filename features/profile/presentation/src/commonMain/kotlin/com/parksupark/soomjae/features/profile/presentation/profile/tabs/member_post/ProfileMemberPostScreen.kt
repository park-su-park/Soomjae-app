package com.parksupark.soomjae.features.profile.presentation.profile.tabs.member_post

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.itemKey
import coil3.compose.AsyncImage
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeCircularProgressIndicator
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaePullToRefreshBox
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.core.presentation.ui.utils.emptyLazyPagingItems
import com.parksupark.soomjae.core.presentation.ui.utils.imageRequest
import com.parksupark.soomjae.features.profile.presentation.profile.tabs.model.MemberPostUi
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ProfileMemberPostScreen(
    state: ProfileMemberPostState,
    onAction: (ProfileMemberPostAction) -> Unit,
    modifier: Modifier = Modifier,
    listState: LazyGridState,
    posts: LazyPagingItems<MemberPostUi>,
) {
    LaunchedEffect(posts.loadState.refresh) {
        val refresh = posts.loadState.refresh
        if (refresh is androidx.paging.LoadState.NotLoading && state.isRefreshing) {
            onAction(ProfileMemberPostAction.RefreshChange(false))
        }
    }

    val isRefreshing = state.isRefreshing && posts.loadState.refresh is LoadState.Loading
    SoomjaePullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = { onAction(ProfileMemberPostAction.OnPullToRefresh) },
        modifier = modifier.fillMaxSize(),
    ) {
        if (state.isRefreshing) {
            SoomjaeCircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            state = listState,
        ) {
            items(count = posts.itemCount, key = posts.itemKey { it.id }) { index ->
                posts[index]?.let { post ->
                    MemberPostGridItem(
                        post = post,
                    )
                }
            }
        }
    }
}

@Composable
private fun MemberPostGridItem(
    post: MemberPostUi,
    modifier: Modifier = Modifier,
) {
    SoomjaeTheme.typography
    AsyncImage(
        model = imageRequest { data(post.imageUrl) },
        contentDescription = null,
        modifier = modifier.fillMaxSize(),
    )
}

@Composable
@Preview(name = "ProfileMemberPost")
private fun ProfileMemberPostScreenPreview() {
    ProfileMemberPostScreen(
        state = ProfileMemberPostState(),
        onAction = { },
        listState = rememberLazyGridState(),
        posts = emptyLazyPagingItems(),
    )
}
