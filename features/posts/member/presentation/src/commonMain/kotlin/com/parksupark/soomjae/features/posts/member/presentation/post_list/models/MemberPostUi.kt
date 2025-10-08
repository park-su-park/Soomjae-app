package com.parksupark.soomjae.features.posts.member.presentation.post_list.models

import androidx.compose.runtime.Composable
import com.parksupark.soomjae.core.presentation.ui.utils.toRelativeTimeString
import com.parksupark.soomjae.features.posts.common.presentation.models.AuthorUi
import com.parksupark.soomjae.features.posts.common.presentation.models.toUi
import com.parksupark.soomjae.features.posts.member.domain.models.MemberPost
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

// TODO: Add more fields later
@OptIn(ExperimentalTime::class)
data class MemberPostUi(
    val id: Long,
    val content: String,
    val author: AuthorUi,
    val createdAt: Instant,
    val isLiked: Boolean,
) {
    val formattedCreatedAt: String
        @Composable get() = createdAt.toRelativeTimeString()
}

@OptIn(ExperimentalTime::class)
internal fun MemberPost.toMemberPostUi(): MemberPostUi = MemberPostUi(
    id = this.id,
    content = this.content,
    author = this.author.toUi(),
    createdAt = this.createdAt,
    isLiked = this.isLiked,
)
