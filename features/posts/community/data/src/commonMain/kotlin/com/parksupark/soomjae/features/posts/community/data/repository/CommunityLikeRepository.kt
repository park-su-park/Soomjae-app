package com.parksupark.soomjae.features.posts.community.data.repository

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.features.posts.common.domain.repositories.LikeRepository
import com.parksupark.soomjae.features.posts.community.data.cache.CommunityPostPatchCache
import com.parksupark.soomjae.features.posts.community.data.remote.source.CommunityLikeRemoteDataSource

internal class CommunityLikeRepository(
    private val patchedCache: CommunityPostPatchCache,
    private val remoteSource: CommunityLikeRemoteDataSource,
) : LikeRepository {
    override suspend fun like(postId: Long): Either<DataFailure, Unit> {
        patchedCache.applyLike(postId, true)

        return remoteSource.postLike(postId).onLeft {
            patchedCache.applyLike(postId, false)
        }
    }

    override suspend fun unlike(postId: Long): Either<DataFailure, Unit> {
        patchedCache.applyLike(postId, false)

        return remoteSource.deleteLike(postId).onLeft {
            patchedCache.applyLike(postId, true)
        }
    }
}
