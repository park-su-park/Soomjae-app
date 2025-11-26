package com.parksupark.soomjae.features.posts.meeting.presentation.tab.filter

import androidx.compose.runtime.Composable
import com.parksupark.soomjae.core.presentation.ui.resources.value
import com.parksupark.soomjae.features.posts.common.presentation.components.FilterKey
import com.parksupark.soomjae.features.posts.meeting.presentation.models.RecruitmentStatusUi
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.Res
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.meeting_detail_filter_chip_recruitment_status
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.meeting_detail_filter_chip_recruitment_status_selected_multiple
import kotlinx.collections.immutable.ImmutableCollection

internal enum class MeetingTabFilterKey : FilterKey {
    Category,

    Location,

    RecruitmentStatus,

    ;

    override val key: String = name
}

@Composable
internal fun buildRecruitmentStatusLabel(
    selected: ImmutableCollection<RecruitmentStatusUi>,
): String = when (selected.size) {
    0 -> Res.string.meeting_detail_filter_chip_recruitment_status.value
    1 -> selected.first().stringResource.value
    else -> Res.string.meeting_detail_filter_chip_recruitment_status_selected_multiple.value(
        selected.first().stringResource.value,
        selected.size - 1,
    )
}
