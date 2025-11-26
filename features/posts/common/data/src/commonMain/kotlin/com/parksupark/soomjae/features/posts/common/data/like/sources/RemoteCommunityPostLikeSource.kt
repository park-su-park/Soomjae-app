package com.parksupark.soomjae.features.posts.common.data.like.sources

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.domain.post.model.Like

interface RemoteCommunityPostLikeSource {
    suspend fun isLiked(postId: Long): Either<DataFailure.Network, Like>
}
