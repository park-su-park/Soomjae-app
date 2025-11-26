package com.parksupark.soomjae.features.posts.common.data.repository

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.features.posts.common.data.cache.MeetingPostPatchCache
import com.parksupark.soomjae.features.posts.common.data.network.datasource.RemoteMeetingLikeDataSource
import com.parksupark.soomjae.features.posts.common.domain.repositories.LikeRepository

internal class DefaultMeetingLikeRepository(
    private val postCache: MeetingPostPatchCache,
    private val remoteDataSource: RemoteMeetingLikeDataSource,
) : LikeRepository {

    override suspend fun like(postId: Long): Either<DataFailure, Unit> {
        postCache.applyLike(postId, liked = true)

        return remoteDataSource.like(postId).onLeft {
            postCache.applyLike(postId, liked = false)
        }
    }

    override suspend fun unlike(postId: Long): Either<DataFailure, Unit> {
        postCache.applyLike(postId, liked = false)

        return remoteDataSource.unlike(postId).onLeft {
            postCache.applyLike(postId, liked = true)
        }
    }
}
