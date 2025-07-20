package com.parksupark.soomjae.features.post.presentation.models

import androidx.compose.runtime.Composable
import com.parksupark.soomjae.core.presentation.ui.utils.toRelativeTimeString
import com.parksupark.soomjae.features.post.domain.models.Comment
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
internal data class CommentUi(
    val id: Long,
    val content: String,
    val author: AuthorUi,
    val createdAt: Instant,
) {
    val formattedCreatedAt: String
        @Composable get() = createdAt.toRelativeTimeString()
}

@OptIn(ExperimentalTime::class)
internal fun Comment.toUi(): CommentUi = CommentUi(
    id = id,
    content = content,
    author = author.toUi(),
    createdAt = createdAt,
)
