package com.parksupark.soomjae.features.posts.meeting.presentation.tab.models

import androidx.compose.runtime.Composable
import com.parksupark.soomjae.core.presentation.ui.utils.toRelativeTimeString
import com.parksupark.soomjae.features.posts.common.domain.models.MeetingPost
import com.parksupark.soomjae.features.posts.common.presentation.models.AuthorUi
import com.parksupark.soomjae.features.posts.common.presentation.models.CategoryUi
import com.parksupark.soomjae.features.posts.common.presentation.models.toUi
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
internal data class MeetingPostUi(
    val id: Long,
    val title: String,
    val content: String,
    val createdAt: Instant,
    val author: AuthorUi,
    val likeCount: Int,
    val commentCount: Int,
    val category: CategoryUi,
) {
    val formattedCreatedAt: String
        @Composable get() = createdAt.toRelativeTimeString()
}

// TODO: implement likeCount and commentCount
@ExperimentalTime
internal fun MeetingPost.toMeetingPostUi() = MeetingPostUi(
    id = id,
    title = title,
    content = content,
    createdAt = createdAt,
    author = author.toUi(),
    likeCount = 0,
    commentCount = 0,
    category = category.toUi(),
)
