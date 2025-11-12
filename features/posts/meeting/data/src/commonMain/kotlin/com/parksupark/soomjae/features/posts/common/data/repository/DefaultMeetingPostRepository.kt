package com.parksupark.soomjae.features.posts.common.data.repository

import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import app.cash.paging.createPager
import app.cash.paging.filter
import app.cash.paging.map
import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.domain.logging.SjLogger
import com.parksupark.soomjae.features.posts.common.data.cache.MeetingPostPatchCache
import com.parksupark.soomjae.features.posts.common.data.dto.request.PostMeetingPostRequest
import com.parksupark.soomjae.features.posts.common.data.dto.response.PostMeetingPostResponse
import com.parksupark.soomjae.features.posts.common.data.dto.response.toMeetingPost
import com.parksupark.soomjae.features.posts.common.data.dto.response.toMeetingPostDetail
import com.parksupark.soomjae.features.posts.common.data.network.datasource.RemoteMeetingPostSource
import com.parksupark.soomjae.features.posts.common.data.paging.MeetingPagingSource
import com.parksupark.soomjae.features.posts.common.domain.models.MeetingPost
import com.parksupark.soomjae.features.posts.common.domain.models.MeetingPostDetail
import com.parksupark.soomjae.features.posts.common.domain.models.MeetingPostFilter
import com.parksupark.soomjae.features.posts.common.domain.models.MeetingPostPatch
import com.parksupark.soomjae.features.posts.common.domain.models.NewPost
import com.parksupark.soomjae.features.posts.common.domain.repositories.MeetingPostRepository
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.datetime.toDeprecatedInstant

@OptIn(ExperimentalTime::class)
internal class DefaultMeetingPostRepository(
    private val logger: SjLogger,
    private val patchCache: MeetingPostPatchCache,
    private val remoteSource: RemoteMeetingPostSource,
) : MeetingPostRepository {

    override suspend fun createPost(
        title: String,
        content: String,
        categoryId: Long?,
        locationCode: Long?,
        startAt: Instant,
        endAt: Instant,
        maxParticipants: Long?,
    ): Either<DataFailure.Network, NewPost> = remoteSource.createPost(
        request = PostMeetingPostRequest(
            title = title,
            content = content,
            categoryId = categoryId,
            locationCode = locationCode,
            startAt = startAt.toDeprecatedInstant(),
            endAt = endAt.toDeprecatedInstant(),
            maxParticipants = maxParticipants ?: -1,
        ),
    ).map { response: PostMeetingPostResponse ->
        NewPost(id = response.id)
    }

    override fun getPostsStream(filter: MeetingPostFilter): Flow<PagingData<MeetingPost>> =
        createPager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                MeetingPagingSource(
                    logger = logger,
                    filter = filter,
                    remoteSource = remoteSource,
                )
            },
        ).flow
            .map { pagingData ->
                pagingData.map { it.toMeetingPost() }
            }

    override suspend fun deletePost(postId: Long): Either<DataFailure, Unit> {
        patchCache.applyDelete(postId)

        return remoteSource.deletePost(postId).onLeft {
            patchCache.removePatch(postId)
        }
    }

    override suspend fun getPostDetail(postId: Long): Either<DataFailure, MeetingPostDetail> =
        remoteSource.fetchPostDetail(postId).map { meetingPostDetail ->
            meetingPostDetail.toMeetingPostDetail()
        }

    override val postPatches: StateFlow<Map<Long, MeetingPostPatch>> =
        patchCache.modifiedPostsStream

    override fun getPatchedPostsStream(filter: MeetingPostFilter): Flow<PagingData<MeetingPost>> =
        combine(
            getPostsStream(filter),
            postPatches,
        ) { data, patches ->
            data.filter { post ->
                patches[post.id] !is MeetingPostPatch.Deleted
            }.map { post ->
                when (val postPatch = patches[post.id]) {
                    is MeetingPostPatch.Updated -> postPatch.post

                    is MeetingPostPatch.LikeChanged -> post.copy(
                        isUserLiked = postPatch.isLiked,
                    )

                    else -> post
                }
            }
        }

    override suspend fun clearPatched() {
        patchCache.clear()
    }
}
