package com.parksupark.soomjae.features.posts.community.data.repository

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.features.posts.common.domain.repositories.LikeRepository
import com.parksupark.soomjae.features.posts.community.data.cache.CommunityPostLikeCache
import com.parksupark.soomjae.features.posts.community.data.remote.source.CommunityLikeRemoteDataSource

internal class CommunityLikeRepository(
    private val likeCache: CommunityPostLikeCache,
    private val remoteSource: CommunityLikeRemoteDataSource,
) : LikeRepository {
    override val cacheStates = likeCache.likeStates

    override suspend fun like(postId: Long): Either<DataFailure, Unit> {
        likeCache.updateLike(postId, true)

        return remoteSource.postLike(postId).onLeft {
            likeCache.updateLike(postId, false)
        }
    }

    override suspend fun unlike(postId: Long): Either<DataFailure, Unit> {
        likeCache.updateLike(postId, false)

        return remoteSource.deleteLike(postId).onLeft {
            likeCache.updateLike(postId, true)
        }
    }
}
