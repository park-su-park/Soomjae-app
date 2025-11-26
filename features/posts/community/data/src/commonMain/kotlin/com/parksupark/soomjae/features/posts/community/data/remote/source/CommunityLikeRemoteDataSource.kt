package com.parksupark.soomjae.features.posts.community.data.remote.source

import com.parksupark.soomjae.core.remote.networking.delete
import com.parksupark.soomjae.core.remote.networking.post
import io.ktor.client.HttpClient

internal class CommunityLikeRemoteDataSource(
    private val httpClient: HttpClient,
) {
    suspend fun postLike(postId: Long) = httpClient.post<Unit, Unit>(
        route = "/v1/boards/community/posts/$postId/like",
        body = Unit,
    )

    suspend fun deleteLike(postId: Long) = httpClient.delete<Unit>(
        route = "/v1/boards/community/posts/$postId/like",
    )
}
