package com.parksupark.soomjae.features.post.domain.repositories

import app.cash.paging.PagingData
import com.parksupark.soomjae.features.post.domain.models.CommunityPost
import kotlinx.coroutines.flow.Flow

interface CommunityRepository {
    fun getAllPosts(): Flow<PagingData<CommunityPost>>
}
