package com.parksupark.soomjae.features.posts.member.data.repository

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import arrow.core.Either
import com.parksupark.soomjae.core.common.paging.createPager
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.features.posts.member.data.source.paging.MemberPostPagingSource
import com.parksupark.soomjae.features.posts.member.data.source.remote.RemoteMemberPostDataSource
import com.parksupark.soomjae.features.posts.member.domain.models.MemberPost
import com.parksupark.soomjae.features.posts.member.domain.repositories.MemberPostRepository
import kotlinx.coroutines.flow.Flow

internal class DefaultMemberPostRepository(
    private val remoteDataSource: RemoteMemberPostDataSource,
) : MemberPostRepository {
    override suspend fun createPost(
        content: String,
        imageUrls: List<String>,
    ): Either<DataFailure, Long> = remoteDataSource.createPost(
        content = content,
        imageUrls = imageUrls,
    ).map {
        it.postId
    }

    override fun getPostsStream(): Flow<PagingData<MemberPost>> = createPager(
        config = PagingConfig(pageSize = 20),
    ) {
        MemberPostPagingSource(remoteDataSource)
    }.flow

    override suspend fun getMemberPostDetail(postId: Long): Either<DataFailure, MemberPost> {
        TODO("Not yet implemented")
    }
}
