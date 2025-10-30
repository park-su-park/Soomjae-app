package com.parksupark.soomjae.features.posts.common.domain.repositories

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure

interface LikeRepository {
    suspend fun like(postId: Long): Either<DataFailure, Unit>

    suspend fun unlike(postId: Long): Either<DataFailure, Unit>
}
