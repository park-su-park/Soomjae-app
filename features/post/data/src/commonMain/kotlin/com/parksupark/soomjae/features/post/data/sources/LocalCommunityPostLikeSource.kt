package com.parksupark.soomjae.features.post.data.sources

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure

interface LocalCommunityPostLikeSource {
    suspend fun isLiked(postId: Long): Either<DataFailure.Network, Boolean>
}
