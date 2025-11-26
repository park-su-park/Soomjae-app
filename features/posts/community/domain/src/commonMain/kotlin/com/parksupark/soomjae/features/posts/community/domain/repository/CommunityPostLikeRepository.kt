package com.parksupark.soomjae.features.posts.community.domain.repository

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.domain.post.model.Like
import kotlinx.coroutines.flow.Flow

interface CommunityPostLikeRepository {
    fun likedStream(postId: Long): Flow<Either<DataFailure, Like>>
}
