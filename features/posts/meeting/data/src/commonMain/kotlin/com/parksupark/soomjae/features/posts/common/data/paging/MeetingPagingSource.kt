package com.parksupark.soomjae.features.posts.common.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.parksupark.soomjae.core.domain.logging.SjLogger
import com.parksupark.soomjae.features.posts.common.data.dto.response.MeetingPostResponse
import com.parksupark.soomjae.features.posts.common.data.network.datasource.RemoteMeetingPostSource
import com.parksupark.soomjae.features.posts.common.domain.models.MeetingPostFilter
import com.parksupark.soomjae.features.posts.common.domain.models.RecruitmentStatus
import kotlinx.io.IOException

internal class MeetingPagingSource(
    private val logger: SjLogger,
    private val remoteSource: RemoteMeetingPostSource,
    private val filter: MeetingPostFilter = MeetingPostFilter(),
) : PagingSource<Int, MeetingPostResponse>() {
    override fun getRefreshKey(state: PagingState<Int, MeetingPostResponse>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1) ?: 1
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MeetingPostResponse> {
        val currentPage = params.key ?: 1

        return try {
            val response = remoteSource.fetchPosts(
                page = currentPage,
                categoryIds = filter.categoryIds,
                locationCodes = filter.locationCodes,
                recruitment = when {
                    filter.recruitmentStatuses.contains(RecruitmentStatus.RECRUITING) -> true
                    filter.recruitmentStatuses.contains(RecruitmentStatus.EXPIRED) -> false
                    else -> null
                },
            )

            response.fold(
                ifLeft = { failure ->
                    LoadResult.Error(Exception(failure.toString()))
                },
                ifRight = { response ->
                    val posts = response.posts
                    LoadResult.Page(
                        data = posts,
                        prevKey = if (currentPage == 1) null else currentPage - 1,
                        nextKey = if (posts.isEmpty()) null else currentPage + 1,
                    )
                },
            )
        } catch (exception: IOException) {
            logger.error(TAG, "IOException occurred while loading meeting posts", exception)
            LoadResult.Error(exception)
        }
    }
}

private const val TAG = "MeetingPagingSource"
