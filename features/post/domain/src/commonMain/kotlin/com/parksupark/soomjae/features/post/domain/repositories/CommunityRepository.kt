package com.parksupark.soomjae.features.post.domain.repositories

import app.cash.paging.PagingData
import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.features.post.domain.models.CommunityPost
import com.parksupark.soomjae.features.post.domain.models.NewPost
import kotlinx.coroutines.flow.Flow

interface CommunityRepository {
    fun getAllPosts(): Flow<PagingData<CommunityPost>>

    suspend fun createPost(
        title: String,
        content: String,
    ): Either<DataFailure.Network, NewPost>

    suspend fun getPostDetails(postId: String): Either<DataFailure.Network, CommunityPost>
}
