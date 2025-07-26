package com.parksupark.soomjae.features.posts.common.presentation.models

import androidx.compose.runtime.Composable
import com.parksupark.soomjae.core.presentation.ui.utils.toRelativeTimeString
import com.parksupark.soomjae.features.posts.common.domain.models.Comment
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
data class CommentUi(
    val id: Long,
    val content: String,
    val author: AuthorUi,
    val createdAt: Instant,
) {
    val formattedCreatedAt: String
        @Composable get() = createdAt.toRelativeTimeString()
}

@OptIn(ExperimentalTime::class)
fun Comment.toUi(): CommentUi = CommentUi(
    id = id,
    content = content,
    author = author.toUi(),
    createdAt = createdAt,
)
