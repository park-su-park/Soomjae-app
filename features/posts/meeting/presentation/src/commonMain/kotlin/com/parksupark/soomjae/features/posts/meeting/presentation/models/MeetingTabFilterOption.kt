package com.parksupark.soomjae.features.posts.meeting.presentation.models

import com.parksupark.soomjae.features.posts.common.presentation.models.CategoryUi
import com.parksupark.soomjae.features.posts.common.presentation.models.LocationUi
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf

data class MeetingTabFilterOption(
    val categories: ImmutableSet<CategoryUi> = persistentSetOf(),
    val locations: ImmutableSet<LocationUi> = persistentSetOf(),
    val recruitmentStatuses: ImmutableSet<RecruitmentStatusUi> = persistentSetOf(),
)
