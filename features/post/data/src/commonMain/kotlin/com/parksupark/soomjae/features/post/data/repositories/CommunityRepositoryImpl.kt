package com.parksupark.soomjae.features.post.data.repositories

import androidx.paging.PagingData
import androidx.paging.map
import app.cash.paging.PagingConfig
import app.cash.paging.createPager
import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.features.post.data.dtos.toCommunityPostDetail
import com.parksupark.soomjae.features.post.data.dtos.toModel
import com.parksupark.soomjae.features.post.data.paging.CommunityPagingSource
import com.parksupark.soomjae.features.post.data.sources.CommunityRemoteSource
import com.parksupark.soomjae.features.post.domain.models.CommunityPost
import com.parksupark.soomjae.features.post.domain.models.CommunityPostDetail
import com.parksupark.soomjae.features.post.domain.models.NewPost
import com.parksupark.soomjae.features.post.domain.repositories.CommunityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

internal class CommunityRepositoryImpl(
    private val remoteSource: CommunityRemoteSource,
) : CommunityRepository {
    override fun getAllPosts(): Flow<PagingData<CommunityPost>> = createPager(
        config = PagingConfig(
            pageSize = 20,
        ),
    ) {
        CommunityPagingSource(remoteSource)
    }.flow
        .map { pagingData ->
            pagingData.map { it.toModel() }
        }

    override suspend fun createPost(
        title: String,
        content: String,
        categoryId: Long,
    ): Either<DataFailure.Network, NewPost> = remoteSource.postPost(
        title = title,
        content = content,
        categoryId = categoryId,
    ).map { response ->
        NewPost(id = response.id)
    }

    override suspend fun getPostDetails(postId: Long): Either<DataFailure.Network, CommunityPost> = remoteSource.getPostDetails(postId)
        .map { response ->
            response.toModel()
        }

    override fun postDetailStream(postId: Long): Flow<Either<DataFailure.Network, CommunityPostDetail>> = flow {
        emit(remoteSource.getPostDetails(postId).map { it.toCommunityPostDetail() })
    }
}
