package com.parksupark.soomjae.features.posts.meeting.presentation.tab

import com.parksupark.soomjae.features.posts.common.presentation.models.CategoryUi
import com.parksupark.soomjae.features.posts.common.presentation.models.LocationUi
import com.parksupark.soomjae.features.posts.meeting.presentation.models.RecruitmentStatusUi
import com.parksupark.soomjae.features.posts.meeting.presentation.tab.filter.MeetingTabFilterEvent
import com.parksupark.soomjae.features.posts.meeting.presentation.tab.filter.MeetingTabFilterState
import com.parksupark.soomjae.features.posts.meeting.presentation.tab.post.MeetingTabPostEvent
import com.parksupark.soomjae.features.posts.meeting.presentation.tab.post.MeetingTabPostState

data class MeetingTabState(
    val filterState: MeetingTabFilterState = MeetingTabFilterState(),
    val postState: MeetingTabPostState = MeetingTabPostState(),
)

sealed interface MeetingTabAction {
    data object OnClick : MeetingTabAction

    data object OnWritePostClick : MeetingTabAction

    data class OnPostClick(val postId: Long) : MeetingTabAction

    data class OnPostLikeClick(val postId: Long) : MeetingTabAction

    data object OnCategoryFilterClick : MeetingTabAction

    data class OnCategorySelect(val categories: List<CategoryUi>) : MeetingTabAction

    data object OnLocationFilterClick : MeetingTabAction

    data class OnLocationSelect(val locations: List<LocationUi>) : MeetingTabAction

    data class OnRecruitmentStatusSelect(val statuses: List<RecruitmentStatusUi>) : MeetingTabAction

    data object OnPullToRefresh : MeetingTabAction

    data class RefreshChange(val refresh: Boolean) : MeetingTabAction
}

sealed interface MeetingTabEvent {
    data class FromFilter(val event: MeetingTabFilterEvent) : MeetingTabEvent

    data class FromPost(val event: MeetingTabPostEvent) : MeetingTabEvent
}
