package com.parksupark.soomjae.features.posts.meeting.presentation.detail.models

import androidx.compose.runtime.Composable
import com.parksupark.soomjae.core.presentation.ui.utils.rememberRemainingTimeText
import com.parksupark.soomjae.features.posts.common.domain.models.RecruitmentPeriod
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
data class RecruitmentPeriodUi(
    val startTime: Instant,
    val endTime: Instant,
) {
    val formattedStartTime: String
        @Composable get() = startTime.rememberRemainingTimeText()
    val formattedEndTime: String
        @Composable get() = endTime.rememberRemainingTimeText()
}

@OptIn(ExperimentalTime::class)
internal fun RecruitmentPeriod.toRecruitmentPeriodUi() = RecruitmentPeriodUi(
    startTime = this.startTime,
    endTime = this.endTime,
)
