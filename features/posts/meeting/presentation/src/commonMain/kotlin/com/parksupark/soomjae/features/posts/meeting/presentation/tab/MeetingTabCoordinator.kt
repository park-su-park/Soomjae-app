package com.parksupark.soomjae.features.posts.meeting.presentation.tab

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewModelScope
import com.parksupark.soomjae.features.posts.common.presentation.PostAction
import com.parksupark.soomjae.features.posts.meeting.presentation.tab.filter.MeetingTabFilterViewModel
import com.parksupark.soomjae.features.posts.meeting.presentation.tab.post.MeetingTabPostViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import org.koin.compose.viewmodel.koinViewModel

class MeetingTabCoordinator(
    private val onPostAction: (PostAction) -> Unit,
    private val filterViewModel: MeetingTabFilterViewModel,
    private val postViewModel: MeetingTabPostViewModel,
    private val viewmodelScope: CoroutineScope,
) {
    internal val screenStateFlow: StateFlow<MeetingTabState> = combine(
        filterViewModel.stateFlow,
        postViewModel.stateFlow,
    ) { filterState, postState ->
        MeetingTabState(
            filterState = filterState,
            postState = postState,
        )
    }.stateIn(
        scope = viewmodelScope,
        started = SharingStarted.Eagerly,
        initialValue = MeetingTabState(),
    )
    internal val posts = postViewModel.posts
    internal val events: Flow<MeetingTabEvent> = merge(
        filterViewModel.events.map { MeetingTabEvent.FromFilter(it) },
        postViewModel.events.map { MeetingTabEvent.FromPost(it) },
    )

    init {
        filterViewModel.stateFlow
            .map { it.selectedOption }
            .distinctUntilChanged()
            .onEach { selectedOption ->
                postViewModel.updateFilterOption(selectedOption)
            }.launchIn(viewmodelScope)
    }

    fun handle(action: MeetingTabAction) {
        when (action) {
            is MeetingTabAction.OnClick -> { // Handle action
            }

            is MeetingTabAction.OnWritePostClick -> postViewModel.handleWritePostClick()

            is MeetingTabAction.OnPostClick -> onPostAction(
                PostAction.NavigateToMeetingDetail(action.postId),
            )

            is MeetingTabAction.OnPostLikeClick -> postViewModel.handlePostLikeClick(action.postId)

            MeetingTabAction.OnCategoryFilterClick -> filterViewModel.loadCategoriesIfNeeded()

            is MeetingTabAction.OnCategorySelect -> filterViewModel.updateSelectedCategories(
                action.categories,
            )

            MeetingTabAction.OnLocationFilterClick -> filterViewModel.loadLocationsIfNeeded()

            is MeetingTabAction.OnLocationSelect -> filterViewModel.updateSelectedLocations(
                action.locations,
            )

            is MeetingTabAction.OnRecruitmentStatusSelect -> filterViewModel.updateSelectedStatuses(
                action.statuses,
            )

            is MeetingTabAction.OnPullToRefresh -> postViewModel.refreshPost()

            is MeetingTabAction.RefreshChange -> postViewModel.setRefreshing(action.refresh)
        }
    }
}

@Composable
fun rememberMeetingTabCoordinator(
    onPostAction: (PostAction) -> Unit,
    filterViewModel: MeetingTabFilterViewModel = koinViewModel(),
    postViewModel: MeetingTabPostViewModel = koinViewModel(),
): MeetingTabCoordinator = remember(onPostAction, filterViewModel, postViewModel) {
    MeetingTabCoordinator(
        onPostAction = onPostAction,
        filterViewModel = filterViewModel,
        postViewModel = postViewModel,
        viewmodelScope = postViewModel.viewModelScope,
    )
}
