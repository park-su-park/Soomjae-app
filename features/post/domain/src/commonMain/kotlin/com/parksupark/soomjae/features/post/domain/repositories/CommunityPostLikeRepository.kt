package com.parksupark.soomjae.features.post.domain.repositories

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.features.post.domain.models.Like
import kotlinx.coroutines.flow.Flow

interface CommunityPostLikeRepository {
    fun likedStream(postId: Long): Flow<Either<DataFailure, Like>>
}
