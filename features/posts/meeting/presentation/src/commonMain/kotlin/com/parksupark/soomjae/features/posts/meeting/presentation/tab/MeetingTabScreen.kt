package com.parksupark.soomjae.features.posts.meeting.presentation.tab

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
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
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeHorizontalDivider
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaePullToRefreshBox
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeScaffold
import com.parksupark.soomjae.core.presentation.ui.components.SoomjaeSnackbarHost
import com.parksupark.soomjae.core.presentation.ui.post.components.MeetingPostCard
import com.parksupark.soomjae.core.presentation.ui.post.model.CategoryUi
import com.parksupark.soomjae.core.presentation.ui.post.model.LocationUi
import com.parksupark.soomjae.core.presentation.ui.post.model.MeetingPostUi
import com.parksupark.soomjae.core.presentation.ui.resources.value
import com.parksupark.soomjae.features.posts.common.presentation.components.FilterChipItem
import com.parksupark.soomjae.features.posts.common.presentation.components.FilterChipRow
import com.parksupark.soomjae.features.posts.common.presentation.components.MultipleSelectionDialog
import com.parksupark.soomjae.features.posts.common.presentation.components.WritePostFab
import com.parksupark.soomjae.features.posts.common.presentation.components.buildCategoryLabel
import com.parksupark.soomjae.features.posts.common.presentation.components.buildLocationLabel
import com.parksupark.soomjae.features.posts.meeting.presentation.models.RecruitmentStatusUi
import com.parksupark.soomjae.features.posts.meeting.presentation.tab.filter.MeetingTabFilterKey
import com.parksupark.soomjae.features.posts.meeting.presentation.tab.filter.MeetingTabFilterState
import com.parksupark.soomjae.features.posts.meeting.presentation.tab.filter.buildRecruitmentStatusLabel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.collections.immutable.toPersistentSet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MeetingTabScreen(
    state: MeetingTabState,
    snackbarHostState: SnackbarHostState,
    onAction: (MeetingTabAction) -> Unit,
    posts: LazyPagingItems<MeetingPostUi>,
    createCache: ImmutableList<MeetingPostUi>,
    listState: LazyListState,
) {
    val postState = state.postState
    val filterState = state.filterState

    RefreshEffect(
        refreshState = posts.loadState.refresh,
        isRefreshing = postState.isPostsRefreshing,
        onRefreshChange = { onAction(MeetingTabAction.RefreshChange(it)) },
    )

    SoomjaeScaffold(
        snackbarHost = { SoomjaeSnackbarHost(snackbarHostState) },
    ) { _ ->
        SoomjaePullToRefreshBox(
            isRefreshing = posts.loadState.refresh is LoadState.Loading,
            onRefresh = { onAction(MeetingTabAction.OnPullToRefresh) },
            modifier = Modifier.fillMaxSize(),
            state = rememberPullToRefreshState(),
        ) {
            LazyColumn(state = listState) {
                stickyHeader {
                    FilterChipRow(
                        state = filterState,
                        onAction = onAction,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
                items(items = createCache, key = { it.id }) {
                    MeetingPostCard(
                        post = it,
                        onFavoriteClick = {
                            onAction(MeetingTabAction.OnPostLikeClick(it.id))
                        },
                        modifier = Modifier.fillMaxWidth()
                            .clickable {
                                onAction(MeetingTabAction.OnPostClick(it.id))
                            }
                            .animateItem(),
                    )
                    SoomjaeHorizontalDivider()
                }
                items(count = posts.itemCount, key = posts.itemKey { it.id }) { index ->
                    posts[index]?.let { post ->
                        MeetingPostCard(
                            post = post,
                            onFavoriteClick = {
                                onAction(MeetingTabAction.OnPostLikeClick(post.id))
                            },
                            modifier = Modifier.fillMaxWidth().clickable {
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
}

@Composable
private fun FilterChipRow(
    state: MeetingTabFilterState,
    onAction: (MeetingTabAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val selectedOption = state.selectedOption

    val filterItems: ImmutableList<FilterChipItem<MeetingTabFilterKey>> = buildFilterItems(
        selectedCategories = selectedOption.categories,
        selectedLocations = selectedOption.locations,
        selectedRecruitmentStatuses = selectedOption.recruitmentStatuses,
    )

    var isCategoryDialogOpen by remember { mutableStateOf(false) }
    var isLocationDialogOpen by remember { mutableStateOf(false) }
    var isRecruitmentStatusDialogOpen by remember { mutableStateOf(false) }

    FilterChipRow(
        filters = filterItems,
        onFilterClick = { key ->
            when (key) {
                MeetingTabFilterKey.Category -> {
                    onAction(MeetingTabAction.OnCategoryFilterClick)
                    isCategoryDialogOpen = true
                }

                MeetingTabFilterKey.Location -> {
                    onAction(MeetingTabAction.OnLocationFilterClick)
                    isLocationDialogOpen = true
                }

                MeetingTabFilterKey.RecruitmentStatus ->
                    isRecruitmentStatusDialogOpen = true
            }
        },
        modifier = modifier,
    )

    when {
        isCategoryDialogOpen -> {
            val categories = state.categories
            MultipleSelectionDialog(
                items = categories.values.toPersistentList(),
                itemKey = { it.id },
                selectedItems = selectedOption.categories.mapNotNull { categories[it.id] }
                    .toPersistentSet(),
                itemName = { it.name },
                onCancelClick = {
                    isCategoryDialogOpen = false
                },
                onConfirmClick = { selectedItems ->
                    onAction(MeetingTabAction.OnCategorySelect(selectedItems.toPersistentList()))
                    isCategoryDialogOpen = false
                },
                onDismissRequest = {
                    isCategoryDialogOpen = false
                },
            )
        }

        isLocationDialogOpen -> {
            val locations = state.locations
            MultipleSelectionDialog(
                items = locations.values.toPersistentList(),
                itemKey = { it.code },
                selectedItems = selectedOption.locations.mapNotNull { locations[it.code] }
                    .toPersistentSet(),
                itemName = { it.name },
                onCancelClick = {
                    isLocationDialogOpen = false
                },
                onConfirmClick = { selectedItems ->
                    onAction(MeetingTabAction.OnLocationSelect(selectedItems.toPersistentList()))
                    isLocationDialogOpen = false
                },
                onDismissRequest = {
                    isLocationDialogOpen = false
                },
            )
        }

        isRecruitmentStatusDialogOpen -> {
            MultipleSelectionDialog(
                items = RecruitmentStatusUi.entries.filter { it.filterable }.toPersistentList(),
                itemKey = { it.name },
                selectedItems = selectedOption.recruitmentStatuses,
                itemName = { it.stringResource.value },
                onCancelClick = {
                    isRecruitmentStatusDialogOpen = false
                },
                onConfirmClick = { selectedItems ->
                    onAction(
                        MeetingTabAction.OnRecruitmentStatusSelect(
                            selectedItems.toPersistentList(),
                        ),
                    )
                    isRecruitmentStatusDialogOpen = false
                },
                onDismissRequest = {
                    isRecruitmentStatusDialogOpen = false
                },
            )
        }
    }
}

@Composable
private fun buildFilterItems(
    selectedCategories: ImmutableSet<CategoryUi>,
    selectedLocations: ImmutableSet<LocationUi>,
    selectedRecruitmentStatuses: ImmutableSet<RecruitmentStatusUi>,
): ImmutableList<FilterChipItem<MeetingTabFilterKey>> = buildList {
    add(
        FilterChipItem(
            key = MeetingTabFilterKey.Category,
            label = buildCategoryLabel(selectedCategories),
            selected = selectedCategories.isNotEmpty(),
        ),
    )
    add(
        FilterChipItem(
            key = MeetingTabFilterKey.Location,
            label = buildLocationLabel(selectedLocations),
            selected = selectedLocations.isNotEmpty(),
        ),
    )
    add(
        FilterChipItem(
            key = MeetingTabFilterKey.RecruitmentStatus,
            label = buildRecruitmentStatusLabel(selectedRecruitmentStatuses),
            selected = selectedRecruitmentStatuses.isNotEmpty(),
        ),
    )
}.toImmutableList()

@Composable
private fun RefreshEffect(
    refreshState: LoadState,
    isRefreshing: Boolean,
    onRefreshChange: (Boolean) -> Unit,
) {
    LaunchedEffect(refreshState, isRefreshing, onRefreshChange) {
        if (refreshState is LoadState.NotLoading && isRefreshing) {
            onRefreshChange(false)
        }
    }
}
