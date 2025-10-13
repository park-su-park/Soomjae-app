package com.parksupark.soomjae.features.profile.presentation.profile.tabs.member_post

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import app.cash.paging.LoadStateLoading
import app.cash.paging.LoadStateNotLoading
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.itemKey
import coil3.compose.AsyncImage
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaePullToRefreshBox
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.core.presentation.ui.resources.value
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
    LaunchedEffect(posts.loadState.refresh, onAction) {
        val refresh = posts.loadState.refresh
        if (refresh is LoadStateNotLoading && state.isRefreshing) {
            onAction(ProfileMemberPostAction.RefreshChange(false))
        }
    }

    val isRefreshing = state.isRefreshing && posts.loadState.refresh is LoadStateLoading
    SoomjaePullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = { onAction(ProfileMemberPostAction.OnPullToRefresh) },
        modifier = modifier.fillMaxSize(),
    ) {
        when {
            isRefreshing -> Unit

            posts.loadState.refresh is LoadState.Error -> Text(
                text = "오류",
                modifier = Modifier.align(Alignment.TopCenter).padding(top = 8.dp),
                color = SoomjaeTheme.colorScheme.text3,
                style = SoomjaeTheme.typography.headlineS,
            )

            posts.itemCount == 0 -> Text(
                text = "텅",
                modifier = Modifier.align(Alignment.TopCenter).padding(top = 8.dp),
                color = SoomjaeTheme.colorScheme.text3,
                style = SoomjaeTheme.typography.headlineS,
            )

            else -> LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxSize(),
                state = listState,
                contentPadding = PaddingValues(top = 2.dp),
                horizontalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                items(count = posts.itemCount, key = posts.itemKey { it.id }) { index ->
                    posts[index]?.let { post ->
                        MemberPostGridItem(post = post)
                    }
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
    AsyncImage(
        model = imageRequest { data(post.imageUrl) },
        contentDescription = null,
        modifier = modifier.fillMaxSize()
            .background(SoomjaeTheme.colorScheme.background2)
            .aspectRatio(4 / 5f),
        placeholder = SoomjaeTheme.drawable.ic_sample_image.value,
        contentScale = ContentScale.Crop,
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
