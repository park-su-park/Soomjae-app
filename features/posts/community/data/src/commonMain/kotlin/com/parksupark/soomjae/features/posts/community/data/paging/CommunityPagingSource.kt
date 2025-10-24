package com.parksupark.soomjae.features.posts.community.data.paging

import app.cash.paging.PagingSource
import app.cash.paging.PagingState
import com.parksupark.soomjae.features.posts.community.data.remote.dto.CommunityPostResponse
import com.parksupark.soomjae.features.posts.community.data.remote.source.CommunityRemoteSource
import kotlinx.io.IOException

internal class CommunityPagingSource(
    private val remoteSource: CommunityRemoteSource,
) : PagingSource<Int, CommunityPostResponse>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CommunityPostResponse> {
        val currentPage = params.key ?: 1

        return try {
            val response = remoteSource.getPosts(currentPage)
            response.fold(
                ifLeft = { failure -> LoadResult.Error(Exception(failure.toString())) },
                ifRight = { posts ->
                    LoadResult.Page(
                        data = posts,
                        prevKey = if (currentPage == 1) null else currentPage - 1,
                        nextKey = if (posts.isEmpty()) null else currentPage + 1,
                    )
                },
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CommunityPostResponse>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1) ?: 1
        }
}
