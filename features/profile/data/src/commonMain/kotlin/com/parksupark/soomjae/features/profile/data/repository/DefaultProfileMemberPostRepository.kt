package com.parksupark.soomjae.features.profile.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.parksupark.soomjae.features.profile.data.datasources.ProfileMemberPostRemoteDataSource
import com.parksupark.soomjae.features.profile.data.paging.ProfileMemberPostPagingSource
import com.parksupark.soomjae.features.profile.domain.models.ProfileMemberPost
import com.parksupark.soomjae.features.profile.domain.repository.ProfileMemberPostRepository
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow

internal class DefaultProfileMemberPostRepository(
    private val httpClient: HttpClient,
) : ProfileMemberPostRepository {
    override fun getPagedPosts(memberId: Long): Flow<PagingData<ProfileMemberPost>> = Pager(
        config = PagingConfig(
            pageSize = 10,
        ),
        pagingSourceFactory = {
            ProfileMemberPostPagingSource(
                memberId = memberId,
                remote = ProfileMemberPostRemoteDataSource(httpClient),
            )
        },
    ).flow
}
