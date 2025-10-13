package com.parksupark.soomjae.features.posts.member.domain.repositories

import app.cash.paging.PagingData
import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.features.posts.member.domain.models.MemberPost
import kotlinx.coroutines.flow.Flow

interface MemberPostRepository {
    suspend fun createPost(
        content: String,
        imageUrls: List<String>,
    ): Either<DataFailure, Long>

    fun getPostsStream(): Flow<PagingData<MemberPost>>

    suspend fun getMemberPostDetail(postId: Long): Either<DataFailure, MemberPost>
}
