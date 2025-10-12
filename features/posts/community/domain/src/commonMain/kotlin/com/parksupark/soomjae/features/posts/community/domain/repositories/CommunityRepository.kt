package com.parksupark.soomjae.features.posts.community.domain.repositories

import app.cash.paging.PagingData
import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.features.posts.common.domain.models.NewPost
import com.parksupark.soomjae.features.posts.community.domain.models.CommunityPost
import com.parksupark.soomjae.features.posts.community.domain.models.CommunityPostDetail
import kotlinx.coroutines.flow.Flow

interface CommunityRepository {
    suspend fun createPost(
        title: String,
        content: String,
        categoryId: Long? = null,
        locationCode: Long? = null,
    ): Either<DataFailure.Network, NewPost>

    fun getAllPosts(): Flow<PagingData<CommunityPost>>

    suspend fun getPostDetails(postId: Long): Either<DataFailure.Network, CommunityPostDetail>

    fun postDetailStream(postId: Long): Flow<Either<DataFailure.Network, CommunityPostDetail>>
}
