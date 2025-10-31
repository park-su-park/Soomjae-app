package com.parksupark.soomjae.features.posts.community.presentation.tab

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewModelScope
import com.parksupark.soomjae.features.posts.common.presentation.PostAction
import com.parksupark.soomjae.features.posts.common.presentation.PostAction.NavigateToCommunityDetail
import com.parksupark.soomjae.features.posts.community.presentation.tab.CommunityTabAction.OnCategoryFilterClick
import com.parksupark.soomjae.features.posts.community.presentation.tab.CommunityTabAction.OnCategoryFilterSelect
import com.parksupark.soomjae.features.posts.community.presentation.tab.CommunityTabAction.OnCommunityWriteClick
import com.parksupark.soomjae.features.posts.community.presentation.tab.CommunityTabAction.OnLocationFilterClick
import com.parksupark.soomjae.features.posts.community.presentation.tab.CommunityTabAction.OnLocationFilterSelect
import com.parksupark.soomjae.features.posts.community.presentation.tab.CommunityTabAction.OnPostClick
import com.parksupark.soomjae.features.posts.community.presentation.tab.CommunityTabAction.OnPullToRefresh
import com.parksupark.soomjae.features.posts.community.presentation.tab.CommunityTabAction.RefreshChange
import com.parksupark.soomjae.features.posts.community.presentation.tab.filter.CommunityTabFilterViewModel
import com.parksupark.soomjae.features.posts.community.presentation.tab.post.CommunityTabPostViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import org.koin.compose.viewmodel.koinViewModel

class CommunityTabCoordinator(
    private val onPostAction: (PostAction) -> Unit,
    private val filterViewModel: CommunityTabFilterViewModel,
    private val postViewModel: CommunityTabPostViewModel,
    private val scope: CoroutineScope,
) {
    val screenStateFlow = combine(
        filterViewModel.stateFlow,
        postViewModel.stateFlow,
    ) { filterState, postState ->
        CommunityTabState(
            filterState = filterState,
            postState = postState,
        )
    }
    val eventFlow = postViewModel.eventChannel

    val posts = postViewModel.posts

    init {
        filterViewModel.stateFlow.map { it.selectedOption }
            .distinctUntilChanged()
            .onEach { filterState ->
                postViewModel.updateFilter(filterOption = filterState)
            }.launchIn(scope)
    }

    internal fun handle(action: CommunityTabAction) {
        when (action) {
            is OnPullToRefresh -> postViewModel.refreshPosts()

            is RefreshChange -> postViewModel.setRefreshing(action.isRefreshing)

            is OnPostClick -> onPostAction(NavigateToCommunityDetail(postId = action.postId))

            is OnCommunityWriteClick -> postViewModel.handleCommunityWriteClick()

            OnCategoryFilterClick -> filterViewModel.loadCategoriesIfNeeded()

            is OnCategoryFilterSelect -> filterViewModel.updateSelectedCategories(action.ids)

            OnLocationFilterClick -> filterViewModel.loadLocationsIfNeeded()

            is OnLocationFilterSelect -> filterViewModel.updateSelectedLocations(action.codes)
        }
    }
}

@Composable
internal fun rememberCommunityTabCoordinator(
    onPostAction: (PostAction) -> Unit,
    filterViewModel: CommunityTabFilterViewModel = koinViewModel(),
    postViewModel: CommunityTabPostViewModel = koinViewModel(),
): CommunityTabCoordinator = remember(onPostAction, filterViewModel, postViewModel) {
    CommunityTabCoordinator(
        onPostAction = onPostAction,
        filterViewModel = filterViewModel,
        postViewModel = postViewModel,
        scope = postViewModel.viewModelScope,
    )
}
