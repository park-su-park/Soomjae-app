package com.parksupark.soomjae.features.posts.community.data.repositories

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.features.posts.common.data.like.sources.RemoteCommunityPostLikeSource
import com.parksupark.soomjae.features.posts.common.domain.models.Like
import com.parksupark.soomjae.features.posts.community.domain.repositories.CommunityPostLikeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class OfflineFirstCommunityPostLikeRepository(
    private val remoteSource: RemoteCommunityPostLikeSource,
) : CommunityPostLikeRepository {
    override fun likedStream(postId: Long): Flow<Either<DataFailure, Like>> = flow {
        // TODO: Emit local cache result if available

        emit(remoteSource.isLiked(postId)).also {
            // TODO: Update local cache with the result
        }
    }
}
