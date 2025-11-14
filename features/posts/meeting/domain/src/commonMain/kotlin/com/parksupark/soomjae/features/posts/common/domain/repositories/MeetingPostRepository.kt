package com.parksupark.soomjae.features.posts.common.domain.repositories

import app.cash.paging.PagingData
import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.features.posts.common.domain.models.CreateMeetingPost
import com.parksupark.soomjae.features.posts.common.domain.models.MeetingPost
import com.parksupark.soomjae.features.posts.common.domain.models.MeetingPostDetail
import com.parksupark.soomjae.features.posts.common.domain.models.MeetingPostFilter
import com.parksupark.soomjae.features.posts.common.domain.models.MeetingPostPatch
import com.parksupark.soomjae.features.posts.common.domain.models.NewPost
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalTime::class)
interface MeetingPostRepository {
    // Post
    suspend fun createPost(
        title: String,
        content: String,
        categoryId: Long?,
        locationCode: Long?,
        startAt: Instant,
        endAt: Instant,
        maxParticipants: Long?,
    ): Either<DataFailure.Network, NewPost>

    fun getPostsStream(filter: MeetingPostFilter): Flow<PagingData<MeetingPost>>

    suspend fun updatePost(updatedPost: CreateMeetingPost): Either<DataFailure, NewPost>

    suspend fun deletePost(id: Long): Either<DataFailure, Unit>

    // Detail
    suspend fun getPostDetail(postId: Long): Either<DataFailure, MeetingPostDetail>

    // Patch
    @Suppress("detekt.ClassOrdering")
    val postPatches: StateFlow<Map<Long, MeetingPostPatch>>

    fun getPatchedPostsStream(filter: MeetingPostFilter): Flow<PagingData<MeetingPost>>

    suspend fun clearPatched()
}
