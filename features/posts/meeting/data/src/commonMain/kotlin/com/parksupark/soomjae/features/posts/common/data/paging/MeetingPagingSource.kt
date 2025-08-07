package com.parksupark.soomjae.features.posts.common.data.paging

import androidx.paging.PagingState
import app.cash.paging.PagingSource
import co.touchlab.kermit.Logger
import com.parksupark.soomjae.features.posts.common.data.dtos.toMeetingPost
import com.parksupark.soomjae.features.posts.common.data.sources.RemoteMeetingPostSource
import com.parksupark.soomjae.features.posts.common.domain.models.MeetingPost
import kotlinx.io.IOException

internal class MeetingPagingSource(
    private val remoteSource: RemoteMeetingPostSource,
) : PagingSource<Int, MeetingPost>() {
    override fun getRefreshKey(state: PagingState<Int, MeetingPost>): Int? = state.anchorPosition?.let { anchorPosition ->
        state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1) ?: 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MeetingPost> {
        val currentPage = params.key ?: 1

        return try {
            val response = remoteSource.getMeetingPosts(currentPage)
            response.fold(
                ifLeft = { failure -> LoadResult.Error(Exception(failure.toString())) },
                ifRight = { response ->
                    val posts = response.posts
                    LoadResult.Page(
                        data = posts.map { it.toMeetingPost() },
                        prevKey = if (currentPage == 1) null else currentPage - 1,
                        nextKey = if (posts.isEmpty()) null else currentPage + 1,
                    )
                },
            )
        } catch (exception: IOException) {
            Logger.e(TAG) { exception.message.toString() }

            LoadResult.Error(exception)
        }
    }
}

private const val TAG = "MeetingPagingSource"
