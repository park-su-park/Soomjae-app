package com.parksupark.soomjae.features.post.data.sources

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.features.post.data.dtos.CommunityPostResponse
import com.parksupark.soomjae.features.post.data.dtos.PostCommunityPostResponse

internal interface CommunityRemoteSource {
    suspend fun getPosts(page: Int): Either<DataFailure.Network, List<CommunityPostResponse>>

    suspend fun postPost(
        title: String,
        content: String,
    ): Either<DataFailure.Network, PostCommunityPostResponse>

    suspend fun getPostDetails(postId: String): Either<DataFailure.Network, CommunityPostResponse>
}
