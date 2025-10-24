package com.parksupark.soomjae.features.posts.community.presentation.models

import com.parksupark.soomjae.features.posts.common.presentation.models.CommentUi
import com.parksupark.soomjae.features.posts.common.presentation.models.toUi
import com.parksupark.soomjae.features.posts.community.domain.model.CommunityPostDetail
import com.parksupark.soomjae.features.posts.community.domain.model.CommunityPostDetailWithLiked
import kotlin.time.ExperimentalTime
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class CommunityPostDetailUi(
    val post: CommunityPostUi,
    val isLiked: Boolean,
    val likeCount: Int,
    val commentCount: Int,
    val comments: ImmutableList<CommentUi>,
)

@OptIn(ExperimentalTime::class)
private fun CommunityPostDetail.toUi(): CommunityPostUi = this.post.toUi()

internal fun CommunityPostDetailWithLiked.toDetailUi(): CommunityPostDetailUi = CommunityPostDetailUi(
    post = this.postDetail.toUi(),
    isLiked = this.isLiked,
    likeCount = this.postDetail.post.likeCount,
    commentCount = this.postDetail.comments.count(),
    comments = this.postDetail.comments.map { it.toUi() }.toImmutableList(),
)
