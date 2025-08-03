package com.parksupark.soomjae.features.posts.common.domain.repositories

import app.cash.paging.PagingData
import com.parksupark.soomjae.features.posts.common.domain.models.MeetingPost
import kotlinx.coroutines.flow.Flow

interface MeetingPostRepository {
    fun getPostsStream(): Flow<PagingData<MeetingPost>>
}
