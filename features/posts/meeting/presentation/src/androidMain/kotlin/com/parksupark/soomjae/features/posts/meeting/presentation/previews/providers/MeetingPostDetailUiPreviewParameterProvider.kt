package com.parksupark.soomjae.features.posts.meeting.presentation.previews.providers

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.parksupark.soomjae.features.posts.common.presentation.models.toUi
import com.parksupark.soomjae.features.posts.common.presentation.previews.providers.CommentPreviewParameterData.comments
import com.parksupark.soomjae.features.posts.meeting.presentation.detail.models.MeetingPostDetailUi
import com.parksupark.soomjae.features.posts.meeting.presentation.detail.models.RecruitmentPeriodUi
import com.parksupark.soomjae.features.posts.meeting.presentation.previews.providers.MeetingPostDetailPreviewParameterData.postDetails
import com.parksupark.soomjae.features.posts.meeting.presentation.previews.providers.MeetingPostPreviewParameterData.posts
import com.parksupark.soomjae.features.posts.meeting.presentation.tab.models.toMeetingPostUi
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime
import kotlin.time.times
import kotlinx.collections.immutable.toImmutableList

internal class MeetingPostDetailUiPreviewParameterProvider : PreviewParameterProvider<MeetingPostDetailUi> {
    override val values: Sequence<MeetingPostDetailUi> = postDetails.asSequence()
}

@OptIn(ExperimentalTime::class)
internal object MeetingPostDetailPreviewParameterData {
    val postDetails = listOf(
        MeetingPostDetailUi(
            post = posts[0].toMeetingPostUi(),
            isLike = true,
            maxParticipationCount = 128,
            currentParticipantCount = 3,
            isUserJoined = true,
            recruitmentPeriod = RecruitmentPeriodUi(
                startTime = posts[0].createdAt,
                endTime = posts[0].createdAt.plus(3600 * 5.seconds),
            ),
            comments = comments.map { it.toUi() }.toImmutableList(),
        ),
    )
}
