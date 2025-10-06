package com.parksupark.soomjae.features.posts.member.data.datasources.remote

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.remote.networking.get
import com.parksupark.soomjae.features.posts.member.data.dtos.response.MemberPostsResponse
import com.parksupark.soomjae.features.posts.member.data.dtos.response.toMemberPost
import com.parksupark.soomjae.features.posts.member.domain.models.MemberPost
import io.ktor.client.HttpClient

internal class RemoteMemberPostDataSource(
    private val httpClient: HttpClient,
) {
    suspend fun getMemberPosts(page: Int): Either<DataFailure.Network, List<MemberPost>> = httpClient.get<MemberPostsResponse>(
        route = "/v1/boards/member/posts/list",
        queryParameters = mapOf("page" to page),
    ).map { response ->
        response.posts.map {
            it.toMemberPost()
        }
    }
}
