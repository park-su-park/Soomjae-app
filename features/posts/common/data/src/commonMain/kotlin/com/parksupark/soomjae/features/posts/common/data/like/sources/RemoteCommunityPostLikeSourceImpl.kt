package com.parksupark.soomjae.features.posts.common.data.like.sources

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.domain.post.model.Like
import com.parksupark.soomjae.core.remote.networking.get
import com.parksupark.soomjae.features.posts.common.data.like.dtos.LikeResponse
import com.parksupark.soomjae.features.posts.common.data.like.dtos.toDomain
import io.ktor.client.HttpClient

internal class RemoteCommunityPostLikeSourceImpl(
    private val httpClient: HttpClient,
) : RemoteCommunityPostLikeSource {
    override suspend fun isLiked(postId: Long): Either<DataFailure.Network, Like> =
        httpClient.get<LikeResponse>(
            route = "/v1/boards/community/posts/$postId/like",
        ).map {
            it.toDomain()
        }
}
