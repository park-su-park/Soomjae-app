package com.parksupark.soomjae.features.posts.common.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.parksupark.soomjae.core.domain.logging.SjLogger
import com.parksupark.soomjae.features.posts.common.data.dto.response.MeetingPostResponse
import com.parksupark.soomjae.features.posts.common.data.network.datasource.RemoteMeetingPostSource
import kotlinx.io.IOException

internal class MeetingByMemberIdPagingSource(
    private val logger: SjLogger,
    private val memberId: Long,
    private val remoteSource: RemoteMeetingPostSource,
) : PagingSource<Int, MeetingPostResponse>() {
    override fun getRefreshKey(state: PagingState<Int, MeetingPostResponse>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1) ?: 1
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MeetingPostResponse> {
        val currentPage = params.key ?: 1

        return try {
            val response = remoteSource.fetchPostsByMemberId(
                page = currentPage,
                memberId = memberId,
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
        } catch (e: IOException) {
            logger.error(TAG, "IOException occurred while loading meeting posts by memberId", e)
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val TAG = "MeetingByMemberIdPaging"
    }
}
