package com.parksupark.soomjae.features.posts.common.domain.repositories

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import kotlinx.coroutines.flow.StateFlow

interface LikeRepository {
    val cacheStates: StateFlow<Map<Long, Boolean>>

    suspend fun like(postId: Long): Either<DataFailure, Unit>

    suspend fun unlike(postId: Long): Either<DataFailure, Unit>
}
