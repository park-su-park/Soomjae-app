package com.parksupark.soomjae.features.post.data.sources

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.features.post.domain.models.Like

interface RemoteCommunityPostLikeSource {
    suspend fun isLiked(postId: Long): Either<DataFailure.Network, Like>
}
