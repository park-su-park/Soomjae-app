package com.parksupark.soomjae.features.posts.member.data.repositories

import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import app.cash.paging.createPager
import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.features.posts.member.data.datasources.remote.RemoteMemberPostDataSource
import com.parksupark.soomjae.features.posts.member.data.paging.MemberPostPagingSource
import com.parksupark.soomjae.features.posts.member.domain.models.MemberPost
import com.parksupark.soomjae.features.posts.member.domain.repositories.MemberPostRepository
import kotlinx.coroutines.flow.Flow

internal class DefaultMemberPostRepository(
    private val remoteDataSource: RemoteMemberPostDataSource,
) : MemberPostRepository {
    override fun getPostsStream(): Flow<PagingData<MemberPost>> = createPager(
        config = PagingConfig(pageSize = 20),
    ) {
        MemberPostPagingSource(remoteDataSource)
    }.flow

    override suspend fun getMemberPostDetail(postId: Long): Either<DataFailure, MemberPost> {
        TODO("Not yet implemented")
    }
}
