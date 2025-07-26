package com.parksupark.soomjae.features.posts.common.presentation.previews.providers

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.parksupark.soomjae.core.presentation.ui.previews.proviers.MemberPreviewParameterData.members
import com.parksupark.soomjae.features.posts.common.domain.models.Comment
import com.parksupark.soomjae.features.posts.common.presentation.models.CommentUi
import com.parksupark.soomjae.features.posts.common.presentation.models.toUi
import kotlin.time.ExperimentalTime
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

class CommentUiPreviewParameterProvider : PreviewParameterProvider<ImmutableList<CommentUi>> {
    override val values: Sequence<ImmutableList<CommentUi>> =
        sequenceOf(CommentPreviewParameterData.comments.map { it.toUi() }.toImmutableList())
}

object CommentPreviewParameterData {
    @OptIn(ExperimentalTime::class)
    val comments = persistentListOf(
        Comment(
            id = 1,
            content = "Great point! I hadn't considered that.",
            author = members[0],
            createdAt = LocalDateTime(2023, 1, 1, 12, 0, 0).toInstant(TimeZone.UTC),
        ),
        Comment(
            id = 2,
            content = "Thanks for the detailed explanation.",
            author = members[1],
            createdAt = LocalDateTime(2023, 1, 2, 15, 30, 0).toInstant(TimeZone.UTC),
        ),
        Comment(
            id = 3,
            content = "This is very helpful, thank you!",
            author = members[2],
            createdAt = LocalDateTime(2023, 1, 3, 9, 45, 0).toInstant(TimeZone.UTC),
        ),
        Comment(
            id = 4,
            content = "I agree with the previous comment.",
            author = members[3],
            createdAt = LocalDateTime(2023, 1, 4, 11, 15, 0).toInstant(TimeZone.UTC),
        ),
        Comment(
            id = 5,
            content = "Could you elaborate on the last point?",
            author = members[0],
            createdAt = LocalDateTime(2023, 1, 5, 14, 0, 0).toInstant(TimeZone.UTC),
        ),
    )
}
