package com.parksupark.soomjae.features.posts.community.domain.repository

import androidx.paging.PagingData
import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.domain.post.model.NewPost
import com.parksupark.soomjae.features.posts.community.domain.model.CommunityPost
import com.parksupark.soomjae.features.posts.community.domain.model.CommunityPostDetail
import com.parksupark.soomjae.features.posts.community.domain.model.CommunityPostEdited
import com.parksupark.soomjae.features.posts.community.domain.model.CommunityPostPatch
import kotlinx.coroutines.flow.Flow

interface CommunityPostRepository {
    suspend fun createPost(
        title: String,
        content: String,
        categoryId: Long? = null,
        locationCode: Long? = null,
    ): Either<DataFailure.Network, NewPost>

    fun getAllPosts(): Flow<PagingData<CommunityPost>>

    fun getAllPosts(
        categoryIds: List<Long>,
        locationCodes: List<Long>,
    ): Flow<PagingData<CommunityPost>>

    suspend fun getPostDetails(postId: Long): Either<DataFailure.Network, CommunityPostDetail>

    fun postDetailStream(postId: Long): Flow<Either<DataFailure.Network, CommunityPostDetail>>

    suspend fun editPost(editedPost: CommunityPostEdited): Either<DataFailure.Network, NewPost>

    suspend fun deletePost(postId: Long): Either<DataFailure.Network, Unit>

    // Patch
    val postPatches: Flow<Map<Long, CommunityPostPatch>>

    suspend fun clearPatched()
}
