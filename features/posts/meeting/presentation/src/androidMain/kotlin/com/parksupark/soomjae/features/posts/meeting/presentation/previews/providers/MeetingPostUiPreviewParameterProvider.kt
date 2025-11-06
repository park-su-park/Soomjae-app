package com.parksupark.soomjae.features.posts.meeting.presentation.previews.providers

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.parksupark.soomjae.core.presentation.ui.previews.proviers.MemberPreviewParameterData.members
import com.parksupark.soomjae.features.posts.common.domain.models.MeetingPost
import com.parksupark.soomjae.features.posts.common.domain.models.RecruitmentPeriod
import com.parksupark.soomjae.features.posts.common.presentation.previews.providers.CategoryUiPreviewParameterData.categories
import com.parksupark.soomjae.features.posts.common.presentation.previews.providers.LocationPreviewParametersData.locations
import com.parksupark.soomjae.features.posts.meeting.presentation.previews.providers.MeetingPostPreviewParameterData.posts
import com.parksupark.soomjae.features.posts.meeting.presentation.tab.models.MeetingPostUi
import com.parksupark.soomjae.features.posts.meeting.presentation.tab.models.toMeetingPostUi
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.ExperimentalTime
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

@OptIn(ExperimentalTime::class)
internal class MeetingPostUiPreviewParameterProvider :
    PreviewParameterProvider<ImmutableList<MeetingPostUi>> {
    override val values: Sequence<ImmutableList<MeetingPostUi>> =
        sequenceOf(posts.map { it.toMeetingPostUi() }.toImmutableList())
}

@Suppress("ktlint:standard:max-line-length")
@OptIn(ExperimentalTime::class)
object MeetingPostPreviewParameterData {
    val posts = persistentListOf(
        MeetingPost(
            id = 1L,
            title = "Android Study Group Kick-off",
            content = "Let's start our Android study group! We'll discuss the basics of Kotlin and Jetpack Compose. Bring your laptops and enthusiasm!",
            createdAt = LocalDateTime(2023, 1, 1, 10, 15, 30).toInstant(TimeZone.UTC),
            author = members[0],
            category = categories[0],
            location = locations[0],
            isUserLiked = false,
            likeCount = 128,
            commentCount = 2,
            maxParticipationCount = 100,
            currentParticipantCount = 12,
            recruitmentPeriod = RecruitmentPeriod(
                startTime = Clock.System.now(),
                endTime = Clock.System.now().plus(7.days),
            ),
        ),
        MeetingPost(
            id = 2L,
            title = "Deep Dive into Coroutines",
            content = "This session will focus on Kotlin Coroutines. We'll explore structured concurrency, dispatchers, and error handling. Recommended for intermediate developers.",
            createdAt = LocalDateTime(2023, 1, 5, 14, 20, 5).toInstant(TimeZone.UTC),
            author = members[1],
            category = categories[1],
            location = null,
            isUserLiked = true,
            likeCount = 128,
            commentCount = 12,
            maxParticipationCount = 100,
            currentParticipantCount = 12,
            recruitmentPeriod = RecruitmentPeriod(
                startTime = Clock.System.now(),
                endTime = Clock.System.now().plus(7.days),
            ),
        ),
        MeetingPost(
            id = 3L,
            title = "Jetpack Compose UI Workshop",
            content =
                """
                Join us for a hands-on workshop on building beautiful UIs with Jetpack Compose.
                We will cover layouts, state management, and theming.
                Please install Android Studio Bumblebee or later.
                """.trimIndent(),
            createdAt = LocalDateTime(2023, 1, 10, 9, 45, 55).toInstant(TimeZone.UTC),
            author = members[2],
            category = categories[2],
            location = locations[5],
            isUserLiked = false,
            likeCount = 128,
            commentCount = 6,
            maxParticipationCount = 100,
            currentParticipantCount = 12,
            recruitmentPeriod = RecruitmentPeriod(
                startTime = Clock.System.now(),
                endTime = Clock.System.now().plus(7.days),
            ),
        ),
        MeetingPost(
            id = 4L,
            title = "Kotlin Multiplatform Discussion",
            content = "Let's discuss the benefits and challenges of Kotlin Multiplatform. We'll share experiences and best practices.",
            createdAt = LocalDateTime(2023, 1, 15, 11, 30, 0).toInstant(TimeZone.UTC),
            author = members[3],
            category = categories[3],
            location = locations[10],
            isUserLiked = true,
            likeCount = 128,
            commentCount = 4,
            maxParticipationCount = 100,
            currentParticipantCount = 12,
            recruitmentPeriod = RecruitmentPeriod(
                startTime = Clock.System.now(),
                endTime = Clock.System.now().plus(7.days),
            ),
        ),
        MeetingPost(
            id = 5L,
            title = "Android Architecture Patterns",
            content = "We'll explore various architecture patterns in Android development, including MVVM, MVI, and Clean Architecture. Bring your questions!",
            createdAt = LocalDateTime(2023, 1, 20, 13, 0, 0).toInstant(TimeZone.UTC),
            author = members[0],
            category = categories[0],
            location = null,
            isUserLiked = false,
            likeCount = 128,
            commentCount = 128,
            maxParticipationCount = 100,
            currentParticipantCount = 12,
            recruitmentPeriod = RecruitmentPeriod(
                startTime = Clock.System.now(),
                endTime = Clock.System.now().plus(7.days),
            ),
        ),
    )
}
