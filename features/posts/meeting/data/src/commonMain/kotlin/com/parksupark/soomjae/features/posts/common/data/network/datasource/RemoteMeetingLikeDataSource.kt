package com.parksupark.soomjae.features.posts.common.data.network.datasource

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.remote.networking.delete
import com.parksupark.soomjae.core.remote.networking.post
import io.ktor.client.HttpClient

class RemoteMeetingLikeDataSource(
    private val httpClient: HttpClient,
) {
    suspend fun like(postId: Long): Either<DataFailure.Network, Unit> = httpClient.post<Unit, Unit>(
        route = "/v1/boards/meeting/posts/$postId/like",
        body = Unit,
    )

    suspend fun unlike(postId: Long): Either<DataFailure.Network, Unit> = httpClient.delete(
        route = "/v1/boards/meeting/posts/$postId/like",
    )
}
