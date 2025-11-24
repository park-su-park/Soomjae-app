package com.parksupark.soomjae.features.posts.common.data.repository

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.map
import arrow.core.Either
import com.parksupark.soomjae.core.common.paging.createPager
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.domain.logging.SjLogger
import com.parksupark.soomjae.core.domain.post.model.MeetingPost
import com.parksupark.soomjae.core.domain.post.model.MeetingPostDetail
import com.parksupark.soomjae.core.domain.post.model.MeetingPostFilter
import com.parksupark.soomjae.core.domain.post.model.MeetingPostPatch
import com.parksupark.soomjae.core.domain.post.model.NewPost
import com.parksupark.soomjae.core.domain.post.model.RecruitmentPeriod
import com.parksupark.soomjae.core.domain.post.model.UpdateMeetingPost
import com.parksupark.soomjae.core.domain.post.repository.MeetingPostRepository
import com.parksupark.soomjae.features.posts.common.data.cache.MeetingPostPatchCache
import com.parksupark.soomjae.features.posts.common.data.dto.request.PostMeetingPostRequest
import com.parksupark.soomjae.features.posts.common.data.dto.response.PostMeetingPostResponse
import com.parksupark.soomjae.features.posts.common.data.dto.response.toMeetingPost
import com.parksupark.soomjae.features.posts.common.data.dto.response.toMeetingPostDetail
import com.parksupark.soomjae.features.posts.common.data.network.datasource.RemoteMeetingPostSource
import com.parksupark.soomjae.features.posts.common.data.network.dto.toPutMeetingPostRequest
import com.parksupark.soomjae.features.posts.common.data.paging.MeetingPagingSource
import com.parksupark.soomjae.features.posts.common.domain.event.MeetingPostEventBus
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
    private val bus: MeetingPostEventBus,
) : MeetingPostRepository {

    override suspend fun createPost(
        title: String,
        content: String,
        categoryId: Long?,
        locationCode: Long?,
        startAt: Instant,
        endAt: Instant,
        maxParticipants: Long?,
    ): Either<DataFailure.Network, NewPost> {
        val maxParticipants = maxParticipants ?: -1
        return remoteSource.createPost(
            request = PostMeetingPostRequest(
                title = title,
                content = content,
                categoryId = categoryId,
                locationCode = locationCode,
                startAt = startAt.toDeprecatedInstant(),
                endAt = endAt.toDeprecatedInstant(),
                maxParticipants = maxParticipants,
            ),
        ).map { response: PostMeetingPostResponse ->
            val newId = response.id
            bus.emitCreated(
                MeetingPost.createNew(
                    id = newId,
                    title = title,
                    content = content,
                    maxParticipationCount = maxParticipants.toInt(),
                    period = RecruitmentPeriod(
                        startTime = startAt,
                        endTime = endAt,
                    ),
                ),
            )
            NewPost(id = newId)
        }
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

    override suspend fun updatePost(updatedPost: UpdateMeetingPost): Either<DataFailure, NewPost> =
        remoteSource.putPost(updatedPost.id, updatedPost.toPutMeetingPostRequest())
            .map { id ->
                NewPost(id = id)
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
