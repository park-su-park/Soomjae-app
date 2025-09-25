package com.parksupark.soomjae.features.posts.community.data.repositories

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.remote.networking.delete
import com.parksupark.soomjae.core.remote.networking.post
import com.parksupark.soomjae.features.posts.common.domain.repositories.LikeRepository
import io.ktor.client.HttpClient

internal class CommunityLikeRepository(
    private val httpClient: HttpClient,
) : LikeRepository {
    override suspend fun like(postId: Long): Either<DataFailure, Unit> = httpClient.post<Unit, Unit>(
        route = "/v1/boards/community/posts/$postId/like",
        body = Unit,
    )

    override suspend fun unlike(postId: Long): Either<DataFailure, Unit> = httpClient.delete(
        route = "/v1/boards/community/posts/$postId/like",
    )
}
