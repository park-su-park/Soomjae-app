package com.parksupark.soomjae.features.posts.common.domain.repositories

import app.cash.paging.PagingData
import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.features.posts.common.domain.models.MeetingPost
import com.parksupark.soomjae.features.posts.common.domain.models.MeetingPostDetail
import com.parksupark.soomjae.features.posts.common.domain.models.MeetingPostFilter
import com.parksupark.soomjae.features.posts.common.domain.models.NewPost
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalTime::class)
interface MeetingPostRepository {
    suspend fun createPost(
        title: String,
        content: String,
        categoryId: Long?,
        locationCode: Long?,
        startAt: Instant,
        endAt: Instant,
        maxParticipants: Long,
    ): Either<DataFailure.Network, NewPost>

    fun getPostsStream(filter: MeetingPostFilter): Flow<PagingData<MeetingPost>>

    suspend fun getPostDetail(postId: Long): Either<DataFailure, MeetingPostDetail>
}
