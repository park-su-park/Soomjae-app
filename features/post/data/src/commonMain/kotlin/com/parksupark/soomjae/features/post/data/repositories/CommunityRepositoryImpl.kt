package com.parksupark.soomjae.features.post.data.repositories

import androidx.paging.PagingData
import androidx.paging.map
import app.cash.paging.PagingConfig
import app.cash.paging.createPager
import com.parksupark.soomjae.features.post.data.dtos.toModel
import com.parksupark.soomjae.features.post.data.paging.CommunityPagingSource
import com.parksupark.soomjae.features.post.data.sources.CommunityRemoteSource
import com.parksupark.soomjae.features.post.domain.models.CommunityPost
import com.parksupark.soomjae.features.post.domain.repositories.CommunityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class CommunityRepositoryImpl(
    private val remoteSource: CommunityRemoteSource,
) : CommunityRepository {
    override fun getAllPosts(): Flow<PagingData<CommunityPost>> = createPager(
        config = PagingConfig(
            pageSize = 20,
        ),
    ) {
        CommunityPagingSource(remoteSource)
    }.flow
        .map { pagingData ->
            pagingData.map { it.toModel() }
        }
}
