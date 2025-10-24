package com.parksupark.soomjae.features.posts.community.data.remote.source

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.features.posts.community.data.remote.dto.CommunityPostDetailResponse
import com.parksupark.soomjae.features.posts.community.data.remote.dto.CommunityPostResponse
import com.parksupark.soomjae.features.posts.community.data.remote.dto.CreateCommunityPostResponse

internal interface CommunityRemoteSource {
    suspend fun getPosts(page: Int): Either<DataFailure.Network, List<CommunityPostResponse>>

    suspend fun postPost(
        title: String,
        content: String,
        categoryId: Long? = null,
        locationCode: Long? = null,
    ): Either<DataFailure.Network, CreateCommunityPostResponse>

    suspend fun getPostDetails(postId: Long): Either<DataFailure.Network, CommunityPostDetailResponse>
}
