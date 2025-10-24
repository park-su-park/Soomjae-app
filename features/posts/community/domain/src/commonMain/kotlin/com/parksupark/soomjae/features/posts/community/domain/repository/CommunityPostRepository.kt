package com.parksupark.soomjae.features.posts.community.domain.repository

import app.cash.paging.PagingData
import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.features.posts.common.domain.models.NewPost
import com.parksupark.soomjae.features.posts.community.domain.model.CommunityPost
import com.parksupark.soomjae.features.posts.community.domain.model.CommunityPostDetail
import kotlinx.coroutines.flow.Flow

interface CommunityPostRepository {
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
