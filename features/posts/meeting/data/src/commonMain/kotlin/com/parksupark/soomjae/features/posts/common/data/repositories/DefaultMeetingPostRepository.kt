package com.parksupark.soomjae.features.posts.common.data.repositories

import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import app.cash.paging.createPager
import com.parksupark.soomjae.features.posts.common.data.paging.MeetingPagingSource
import com.parksupark.soomjae.features.posts.common.data.sources.RemoteMeetingPostSource
import com.parksupark.soomjae.features.posts.common.domain.models.MeetingPost
import com.parksupark.soomjae.features.posts.common.domain.repositories.MeetingPostRepository
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow

class DefaultMeetingPostRepository(
    private val httpClient: HttpClient,
) : MeetingPostRepository {
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
