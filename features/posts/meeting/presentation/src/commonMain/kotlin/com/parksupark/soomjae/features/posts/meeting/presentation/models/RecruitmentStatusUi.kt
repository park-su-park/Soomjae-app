package com.parksupark.soomjae.features.posts.meeting.presentation.models

import com.parksupark.soomjae.features.posts.common.domain.models.RecruitmentStatus
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.Res
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.recruitment_status_expired
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.recruitment_status_full
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.recruitment_status_joined
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.recruitment_status_recruiting
import org.jetbrains.compose.resources.StringResource

enum class RecruitmentStatusUi(
    val stringResource: StringResource,
    val filterable: Boolean,
) {

    RECRUITING(Res.string.recruitment_status_recruiting, filterable = true),

    FULL(Res.string.recruitment_status_full, filterable = false),

    EXPIRED(Res.string.recruitment_status_expired, filterable = true),

    JOINED(Res.string.recruitment_status_joined, filterable = true),
}

fun RecruitmentStatus.toUi(): RecruitmentStatusUi = when (this) {
    RecruitmentStatus.RECRUITING -> RecruitmentStatusUi.RECRUITING
    RecruitmentStatus.FULL -> RecruitmentStatusUi.FULL
    RecruitmentStatus.EXPIRED -> RecruitmentStatusUi.EXPIRED
    RecruitmentStatus.JOINED -> RecruitmentStatusUi.JOINED
}
