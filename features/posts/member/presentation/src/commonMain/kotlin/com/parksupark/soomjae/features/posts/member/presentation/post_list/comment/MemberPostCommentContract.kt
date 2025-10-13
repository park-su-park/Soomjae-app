package com.parksupark.soomjae.features.posts.member.presentation.post_list.comment

import com.parksupark.soomjae.features.posts.common.presentation.models.CommentUi
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class MemberPostCommentState(
    val isLoading: Boolean = true,
    val comments: ImmutableList<CommentUi> = persistentListOf(),
)
