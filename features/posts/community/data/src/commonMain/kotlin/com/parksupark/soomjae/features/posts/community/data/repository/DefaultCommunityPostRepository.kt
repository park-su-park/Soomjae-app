package com.parksupark.soomjae.features.posts.community.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import app.cash.paging.createPager
import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.features.posts.common.domain.models.NewPost
import com.parksupark.soomjae.features.posts.community.data.cache.CommunityPostPatchCache
import com.parksupark.soomjae.features.posts.community.data.paging.CommunityPagingSource
import com.parksupark.soomjae.features.posts.community.data.remote.dto.toCommunityPostDetail
import com.parksupark.soomjae.features.posts.community.data.remote.dto.toModel
import com.parksupark.soomjae.features.posts.community.data.remote.source.CommunityRemoteSource
import com.parksupark.soomjae.features.posts.community.domain.model.CommunityPost
import com.parksupark.soomjae.features.posts.community.domain.model.CommunityPostDetail
import com.parksupark.soomjae.features.posts.community.domain.model.CommunityPostEdited
import com.parksupark.soomjae.features.posts.community.domain.model.CommunityPostPatch
import com.parksupark.soomjae.features.posts.community.domain.model.toCommunityPost
import com.parksupark.soomjae.features.posts.community.domain.repository.CommunityPostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

internal class DefaultCommunityPostRepository(
    private val patchCache: CommunityPostPatchCache,
    private val remoteSource: CommunityRemoteSource,
) : CommunityPostRepository {
    override val postPatches: Flow<Map<Long, CommunityPostPatch>> =
        patchCache.modifiedPostsStream

    override suspend fun createPost(
        title: String,
        content: String,
        categoryId: Long?,
        locationCode: Long?,
    ): Either<DataFailure.Network, NewPost> = remoteSource.postPost(
        title = title,
        content = content,
        categoryId = categoryId,
        locationCode = locationCode,
    ).map { response ->
        NewPost(id = response.id)
    }

    override fun getAllPosts(): Flow<PagingData<CommunityPost>> = createPager(
        config = app.cash.paging.PagingConfig(
            pageSize = 20,
        ),
    ) {
        CommunityPagingSource(remoteSource)
    }.flow
        .map { pagingData ->
            pagingData.map { it.toModel() }
        }

    override suspend fun getPostDetails(
        postId: Long,
    ): Either<DataFailure.Network, CommunityPostDetail> = remoteSource.getPostDetails(postId)
        .map { response ->
            response.toCommunityPostDetail()
        }

    override fun postDetailStream(
        postId: Long,
    ): Flow<Either<DataFailure.Network, CommunityPostDetail>> = flow {
        emit(remoteSource.getPostDetails(postId).map { it.toCommunityPostDetail() })
    }

    override suspend fun editPost(
        editedPost: CommunityPostEdited,
    ): Either<DataFailure.Network, NewPost> {
        patchCache.applyUpdate(editedPost.toCommunityPost())

        return remoteSource.putPost(
            postId = editedPost.id,
            title = editedPost.title,
            content = editedPost.content,
            categoryId = editedPost.category?.id,
            locationCode = editedPost.location?.code,
        ).map { response ->
            NewPost(id = response)
        }.onLeft {
            patchCache.removePatch(editedPost.id)
        }
    }

    override suspend fun deletePost(postId: Long): Either<DataFailure.Network, Unit> {
        patchCache.applyDelete(postId)

        return remoteSource.deletePost(postId).onLeft {
            patchCache.removePatch(postId)
        }
    }

    override suspend fun clearPatched() {
        patchCache.clear()
    }
}
