package com.parksupark.soomjae.features.profile.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import co.touchlab.kermit.Logger
import com.parksupark.soomjae.features.profile.data.datasources.ProfileMemberPostRemoteDataSource
import com.parksupark.soomjae.features.profile.domain.models.ProfileMemberPost

private const val TAG = "ProfileMemberPostPagingSource"

internal class ProfileMemberPostPagingSource(
    private val memberId: Long,
    private val remote: ProfileMemberPostRemoteDataSource,
) : PagingSource<Int, ProfileMemberPost>() {
    override fun getRefreshKey(state: PagingState<Int, ProfileMemberPost>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProfileMemberPost> {
        val page = params.key ?: 1

        remote.fetchPosts(memberId = memberId, page = page).fold(
            ifLeft = {
                Logger.e(TAG) { "Error fetching profile member posts: $it" }
                return LoadResult.Error(Exception("Failed to load posts"))
            },
            ifRight = {
                val nextKey = if (it.isEmpty()) null else page + 1

                return LoadResult.Page(
                    data = it,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = nextKey,
                )
            },
        )
    }
}
