package com.parksupark.soomjae.features.posts.community.presentation.tab

import androidx.compose.foundation.background
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeCircularProgressIndicator
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeFilterChip
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeHorizontalDivider
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaePullToRefreshBox
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeScaffold
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.core.presentation.ui.paging.emptyLazyPagingItems
import com.parksupark.soomjae.core.presentation.ui.resources.value
import com.parksupark.soomjae.features.posts.common.presentation.components.MultipleSelectionDialog
import com.parksupark.soomjae.features.posts.common.presentation.components.WritePostFab
import com.parksupark.soomjae.features.posts.common.presentation.models.CategoryUi
import com.parksupark.soomjae.features.posts.common.presentation.models.LocationUi
import com.parksupark.soomjae.features.posts.community.presentation.models.CommunityFilterOption
import com.parksupark.soomjae.features.posts.community.presentation.models.CommunityPostUi
import com.parksupark.soomjae.features.posts.community.presentation.resources.Res
import com.parksupark.soomjae.features.posts.community.presentation.resources.community_tab_filter_chip_category
import com.parksupark.soomjae.features.posts.community.presentation.resources.community_tab_filter_chip_category_selected
import com.parksupark.soomjae.features.posts.community.presentation.resources.community_tab_filter_chip_location
import com.parksupark.soomjae.features.posts.community.presentation.resources.community_tab_filter_chip_location_selected
import com.parksupark.soomjae.features.posts.community.presentation.tab.components.CommunityPostCard
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toPersistentList
import kotlinx.collections.immutable.toPersistentSet
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
        if (refresh is LoadState.NotLoading && postState.isPostsRefreshing) {
            onAction(CommunityTabAction.RefreshChange(false))
        }
    }

    SoomjaeScaffold(modifier = Modifier.fillMaxSize()) { _ ->
        val isRefreshing =
            postState.isPostsRefreshing && posts.loadState.refresh is LoadState.Loading

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
                if (refresh is LoadState.Loading && !isRefreshing) {
                    item {
                        SoomjaeCircularProgressIndicator()
                    }
                } else if (refresh is LoadState.Error && !isRefreshing) {
                    item {
                        Text(refresh.error.message!!)
                    }
                } else {
                    stickyHeader {
                        FilterChipsRow(
                            categories = filterState.categories,
                            onCategoryClick = {
                                onAction(CommunityTabAction.OnCategoryFilterClick)
                            },
                            onCategorySelect = { ids ->
                                onAction(CommunityTabAction.OnCategoryFilterSelect(ids))
                            },
                            locations = filterState.locations,
                            onLocationClick = {
                                onAction(CommunityTabAction.OnLocationFilterClick)
                            },
                            selectedFilter = filterState.selectedOption,
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
    categories: ImmutableMap<Long, CategoryUi>,
    onCategoryClick: () -> Unit,
    onCategorySelect: (ids: List<Long>) -> Unit,
    locations: ImmutableMap<Long, LocationUi>,
    onLocationClick: () -> Unit,
    selectedFilter: CommunityFilterOption,
    onLocationSelect: (codes: List<Long>) -> Unit,
    modifier: Modifier = Modifier,
) {
    val selectedCategories = selectedFilter.categories
    val selectedLocations = selectedFilter.locations

    var isCategoryDialogOpen by remember {
        mutableStateOf(false)
    }
    var isLocationDialogOpen by remember {
        mutableStateOf(false)
    }

    LazyRow(
        modifier = modifier.fillMaxWidth().background(color = SoomjaeTheme.colorScheme.background1),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) {
        item {
            val selected = selectedCategories.isNotEmpty()
            SoomjaeFilterChip(
                selected = selected,
                onClick = {
                    onCategoryClick()
                    isCategoryDialogOpen = true
                },
                label = {
                    when {
                        selectedCategories.size > 1 -> Text(
                            Res.string.community_tab_filter_chip_category_selected.value(
                                selectedCategories.first().name,
                                selectedCategories.size - 1,
                            ),
                        )

                        selectedCategories.size == 1 -> Text(selectedCategories.first().name)

                        else -> Text(Res.string.community_tab_filter_chip_category.value)
                    }
                },
            )
        }
        item {
            SoomjaeFilterChip(
                selected = selectedLocations.isNotEmpty(),
                onClick = {
                    onLocationClick()
                    isLocationDialogOpen = true
                },
                label = {
                    when {
                        selectedLocations.size > 1 -> Text(
                            Res.string.community_tab_filter_chip_location_selected.value(
                                selectedLocations.first().name,
                                selectedLocations.size - 1,
                            ),
                        )

                        selectedLocations.size == 1 -> Text(selectedLocations.first().name)

                        else -> Text(Res.string.community_tab_filter_chip_location.value)
                    }
                },
            )
        }
    }

    if (isCategoryDialogOpen) {
        MultipleSelectionDialog(
            items = categories.values.toPersistentList(),
            itemKey = { it.id },
            selectedItems = selectedCategories.mapNotNull { categories[it.id] }.toPersistentSet(),
            itemName = { it.name },
            onCancelClick = {
                isCategoryDialogOpen = false
            },
            onConfirmClick = { selectedItems ->
                onCategorySelect(selectedItems.map { it.id })
                isCategoryDialogOpen = false
            },
            onDismissRequest = {
                isCategoryDialogOpen = false
            },
        )
    } else if (isLocationDialogOpen) {
        MultipleSelectionDialog(
            items = locations.values.toPersistentList(),
            itemKey = { it.code },
            selectedItems = selectedLocations.mapNotNull { locations[it.code] }.toPersistentSet(),
            itemName = { it.name },
            onCancelClick = {
                isLocationDialogOpen = false
            },
            onConfirmClick = { selectedItems ->
                onLocationSelect(selectedItems.map { it.code })
                isLocationDialogOpen = false
            },
            onDismissRequest = {
                isLocationDialogOpen = false
            },
        )
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
