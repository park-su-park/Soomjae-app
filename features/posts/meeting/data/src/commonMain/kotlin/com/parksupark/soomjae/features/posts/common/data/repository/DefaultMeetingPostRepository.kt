package com.parksupark.soomjae.features.posts.common.data.repository

import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import app.cash.paging.createPager
import app.cash.paging.map
import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.domain.logging.SjLogger
import com.parksupark.soomjae.core.remote.networking.get
import com.parksupark.soomjae.core.remote.networking.post
import com.parksupark.soomjae.features.posts.common.data.dto.request.PostMeetingPostRequest
import com.parksupark.soomjae.features.posts.common.data.dto.response.MeetingPostDetailResponse
import com.parksupark.soomjae.features.posts.common.data.dto.response.PostMeetingPostResponse
import com.parksupark.soomjae.features.posts.common.data.dto.response.toMeetingPost
import com.parksupark.soomjae.features.posts.common.data.dto.response.toMeetingPostDetail
import com.parksupark.soomjae.features.posts.common.data.network.datasource.RemoteMeetingPostSource
import com.parksupark.soomjae.features.posts.common.data.paging.MeetingPagingSource
import com.parksupark.soomjae.features.posts.common.domain.models.MeetingPost
import com.parksupark.soomjae.features.posts.common.domain.models.MeetingPostDetail
import com.parksupark.soomjae.features.posts.common.domain.models.MeetingPostFilter
import com.parksupark.soomjae.features.posts.common.domain.models.NewPost
import com.parksupark.soomjae.features.posts.common.domain.repositories.MeetingPostRepository
import io.ktor.client.HttpClient
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.toDeprecatedInstant

@OptIn(ExperimentalTime::class)
internal class DefaultMeetingPostRepository(
    private val logger: SjLogger,
    private val httpClient: HttpClient,
) : MeetingPostRepository {

    override suspend fun postPost(
        title: String,
        content: String,
        categoryId: Long?,
        locationCode: Long?,
        startAt: Instant,
        endAt: Instant,
        maxParticipants: Long,
    ): Either<DataFailure.Network, NewPost> =
        httpClient.post<PostMeetingPostRequest, PostMeetingPostResponse>(
            route = "/v1/boards/meeting/posts",
            body = PostMeetingPostRequest(
                title = title,
                content = content,
                categoryId = categoryId,
                locationCode = locationCode,
                startAt = startAt.toDeprecatedInstant(),
                endAt = endAt.toDeprecatedInstant(),
                maxParticipants = maxParticipants,
            ),
        ).map {
            NewPost(it.id)
        }

    override fun getPostsStream(): Flow<PagingData<MeetingPost>> = createPager(
        config = PagingConfig(
            pageSize = 20,
        ),
        pagingSourceFactory = {
            MeetingPagingSource(
                logger = logger,
                remoteSource = RemoteMeetingPostSource(httpClient),
            )
        },
    ).flow.map { pagingData ->
        pagingData.map { it.toMeetingPost() }
    }

    override fun getPostsStream(filter: MeetingPostFilter): Flow<PagingData<MeetingPost>> =
        createPager(
            config = PagingConfig(
                pageSize = 20,
            ),
            pagingSourceFactory = {
                MeetingPagingSource(
                    logger = logger,
                    filter = filter,
                    remoteSource = RemoteMeetingPostSource(httpClient),
                )
            },
        ).flow.map { pagingData ->
            pagingData.map { it.toMeetingPost() }
        }

    override suspend fun getMeetingPostDetail(
        postId: Long,
    ): Either<DataFailure, MeetingPostDetail> = httpClient.get<MeetingPostDetailResponse>(
        route = "/v1/boards/meeting/posts/$postId",
    ).map { meetingPostDetail ->
        meetingPostDetail.toMeetingPostDetail()
    }
}
