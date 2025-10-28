package com.parksupark.soomjae.features.posts.community.presentation.tab

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeFilterChip
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeHorizontalDivider
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaePullToRefreshBox
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeScaffold
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.core.presentation.ui.utils.emptyLazyPagingItems
import com.parksupark.soomjae.features.posts.common.presentation.components.WritePostFab
import com.parksupark.soomjae.features.posts.community.presentation.models.CommunityFilterOption
import com.parksupark.soomjae.features.posts.community.presentation.models.CommunityPostUi
import com.parksupark.soomjae.features.posts.community.presentation.tab.components.CommunityPostCard
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CommunityTabScreen(
    state: CommunityTabState,
    onAction: (CommunityTabAction) -> Unit,
    posts: LazyPagingItems<CommunityPostUi>,
) {
    val filterState = state.filterState
    val postState = state.postState

    LaunchedEffect(posts.loadState.refresh, onAction) {
        val refresh = posts.loadState.refresh
        if (refresh is LoadStateNotLoading && postState.isPostsRefreshing) {
            onAction(CommunityTabAction.RefreshChange(false))
        }
    }

    SoomjaeScaffold(modifier = Modifier.fillMaxSize()) { _ ->
        val isRefreshing =
            postState.isPostsRefreshing && posts.loadState.refresh is LoadStateLoading

        SoomjaePullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = { onAction(CommunityTabAction.OnPullToRefresh) },
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
                    stickyHeader {
                        FilterChipsRow(
                            selectedFilter = filterState.selectedOption,
                            onCategorySelect = { ids ->
                                onAction(CommunityTabAction.OnCategoryFilterSelect(ids))
                            },
                            onLocationSelect = { codes ->
                                onAction(CommunityTabAction.OnLocationFilterSelect(codes))
                            },
                        )
                    }

                    items(count = posts.itemCount, key = posts.itemKey { it.id }) { index ->
                        posts[index]?.let { post ->
                            CommunityPostCard(
                                post = post,
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

            WritePostFab(
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 8.dp),
                onClick = { onAction(CommunityTabAction.OnCommunityWriteClick) },
            )
        }
    }
}

@Composable
private fun FilterChipsRow(
    selectedFilter: CommunityFilterOption,
    onCategorySelect: (ids: List<Long>) -> Unit,
    onLocationSelect: (codes: List<Long>) -> Unit,
    modifier: Modifier = Modifier,
) {
    val selectedCategories = selectedFilter.categories
    val selectedLocations = selectedFilter.locations

    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) {
        item {
            SoomjaeFilterChip(
                selected = selectedCategories.isNotEmpty(),
                onClick = { },
                label = { Text("Categories") },
            )
        }
        item {
            SoomjaeFilterChip(
                selected = selectedLocations.isNotEmpty(),
                onClick = { },
                label = { Text("Locations") },
            )
        }
    }
}

@Preview
@Composable
private fun CommunityTabScreenPreview() {
    AppTheme {
        CommunityTabScreen(
            state = CommunityTabState(),
            onAction = { },
            posts = emptyLazyPagingItems(),
        )
    }
}
