package com.parksupark.soomjae.features.posts.member.domain.repositories

import app.cash.paging.PagingData
import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.features.posts.member.domain.models.MemberPost
import kotlinx.coroutines.flow.Flow

interface MemberPostRepository {
    fun getPostsStream(): Flow<PagingData<MemberPost>>

    suspend fun getMemberPostDetail(postId: Long): Either<DataFailure, MemberPost>
}
