package com.parksupark.soomjae.features.posts.member.data.source.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import co.touchlab.kermit.Logger
import com.parksupark.soomjae.features.posts.member.data.source.remote.RemoteMemberPostDataSource
import com.parksupark.soomjae.features.posts.member.domain.models.MemberPost

internal class MemberPostPagingSource(
    private val remoteSource: RemoteMemberPostDataSource,
) : PagingSource<Int, MemberPost>() {
    override fun getRefreshKey(state: PagingState<Int, MemberPost>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1) ?: 1
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MemberPost> {
        val currentPage = params.key ?: 1

        val response = remoteSource.getMemberPosts(currentPage)
        response.fold(
            ifLeft = { failure ->
                Logger.e(TAG) { failure.toString() }
                return LoadResult.Error(Exception(failure.toString()))
            },
            ifRight = { posts ->
                return LoadResult.Page(
                    data = posts,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (posts.isEmpty()) null else currentPage + 1,
                )
            },
        )
    }
}

private const val TAG = "MemberPostPagingSource"
