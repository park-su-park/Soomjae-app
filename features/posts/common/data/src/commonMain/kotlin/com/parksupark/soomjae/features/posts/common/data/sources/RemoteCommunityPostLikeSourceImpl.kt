package com.parksupark.soomjae.features.posts.common.data.sources

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.remote.networking.get
import com.parksupark.soomjae.features.posts.common.data.dtos.LikeResponse
import com.parksupark.soomjae.features.posts.common.data.dtos.toDomain
import com.parksupark.soomjae.features.posts.common.domain.models.Like
import io.ktor.client.HttpClient

class RemoteCommunityPostLikeSourceImpl(
    private val httpClient: HttpClient,
) : RemoteCommunityPostLikeSource {
    override suspend fun isLiked(postId: Long): Either<DataFailure.Network, Like> = httpClient.get<LikeResponse>(
        route = "/v1/boards/community/posts/$postId/like",
    ).map {
        it.toDomain()
    }
}
