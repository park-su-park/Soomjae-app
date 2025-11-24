package com.parksupark.soomjae.core.domain.post.repository

import androidx.paging.PagingData
import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.domain.post.model.MeetingPost
import com.parksupark.soomjae.core.domain.post.model.MeetingPostDetail
import com.parksupark.soomjae.core.domain.post.model.MeetingPostFilter
import com.parksupark.soomjae.core.domain.post.model.MeetingPostPatch
import com.parksupark.soomjae.core.domain.post.model.NewPost
import com.parksupark.soomjae.core.domain.post.model.UpdateMeetingPost
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

    fun getByMemberId(memberId: Long): Flow<PagingData<MeetingPost>>

    suspend fun updatePost(updatedPost: UpdateMeetingPost): Either<DataFailure, NewPost>

    suspend fun deletePost(id: Long): Either<DataFailure, Unit>

    // Detail
    suspend fun getPostDetail(postId: Long): Either<DataFailure, MeetingPostDetail>

    // Patch
    @Suppress("detekt.ClassOrdering")
    val postPatches: StateFlow<Map<Long, MeetingPostPatch>>

    fun getPatchedPostsStream(filter: MeetingPostFilter): Flow<PagingData<MeetingPost>>

    suspend fun clearPatched()
}
