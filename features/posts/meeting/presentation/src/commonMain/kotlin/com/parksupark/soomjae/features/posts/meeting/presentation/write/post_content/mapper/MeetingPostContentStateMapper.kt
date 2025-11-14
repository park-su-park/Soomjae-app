package com.parksupark.soomjae.features.posts.meeting.presentation.write.post_content.mapper

import com.parksupark.soomjae.features.posts.common.domain.models.CreateMeetingPost
import com.parksupark.soomjae.features.posts.common.domain.models.RecruitmentPeriod
import com.parksupark.soomjae.features.posts.meeting.presentation.models.DateTimeRangeUi
import com.parksupark.soomjae.features.posts.meeting.presentation.write.post_content.MeetingPostContentState
import kotlin.time.ExperimentalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

internal fun MeetingPostContentState.toCreateMeetingPost(
    id: Long,
    timeZone: TimeZone,
): CreateMeetingPost = CreateMeetingPost(
    id = id,
    title = this.inputTitle.text.toString(),
    content = this.inputContent.text.toString(),
    categoryId = this.selectedCategory?.id,
    locationCode = this.selectedLocation?.code,
    maxParticipationCount = if (this.meetingForm.participantLimit.isUnlimited) {
        null
    } else {
        this.meetingForm.participantLimit.limitCount.text.toString().toIntOrNull() ?: Int.MAX_VALUE
    },
    recruitmentPeriod = this.meetingForm.period.toRecruitmentPeriod(timeZone),
)

@OptIn(ExperimentalTime::class)
private fun DateTimeRangeUi.toRecruitmentPeriod(timeZone: TimeZone): RecruitmentPeriod =
    RecruitmentPeriod(
        startTime = this.startDateTime.toInstant(timeZone),
        endTime = this.endDateTime.toInstant(timeZone),
    )
