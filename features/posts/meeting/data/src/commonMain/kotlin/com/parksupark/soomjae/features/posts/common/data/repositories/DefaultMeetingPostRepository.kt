package com.parksupark.soomjae.features.posts.common.data.repositories

import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import app.cash.paging.createPager
import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.remote.networking.post
import com.parksupark.soomjae.features.posts.common.data.dtos.PostMeetingPostRequest
import com.parksupark.soomjae.features.posts.common.data.dtos.PostMeetingPostResponse
import com.parksupark.soomjae.features.posts.common.data.paging.MeetingPagingSource
import com.parksupark.soomjae.features.posts.common.data.sources.RemoteMeetingPostSource
import com.parksupark.soomjae.features.posts.common.domain.models.MeetingPost
import com.parksupark.soomjae.features.posts.common.domain.models.NewPost
import com.parksupark.soomjae.features.posts.common.domain.repositories.MeetingPostRepository
import io.ktor.client.HttpClient
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.toDeprecatedInstant

@OptIn(ExperimentalTime::class)
class DefaultMeetingPostRepository(
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
    ): Either<DataFailure.Network, NewPost> = httpClient.post<PostMeetingPostRequest, PostMeetingPostResponse>(
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
                remoteSource = RemoteMeetingPostSource(httpClient),
            )
        },
    ).flow
}
