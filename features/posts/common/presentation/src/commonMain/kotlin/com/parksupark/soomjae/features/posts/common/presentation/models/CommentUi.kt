package com.parksupark.soomjae.features.posts.common.presentation.models

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import com.parksupark.soomjae.core.domain.post.model.Comment
import com.parksupark.soomjae.core.presentation.ui.utils.toRelativeTimeString
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
@Immutable
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
