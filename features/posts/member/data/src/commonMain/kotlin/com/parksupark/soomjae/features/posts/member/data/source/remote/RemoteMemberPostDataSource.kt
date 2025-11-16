package com.parksupark.soomjae.features.posts.member.data.source.remote

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.remote.networking.get
import com.parksupark.soomjae.core.remote.networking.post
import com.parksupark.soomjae.features.posts.member.data.model.dto.request.PostMemberPostRequest
import com.parksupark.soomjae.features.posts.member.data.model.dto.response.FetchMemberPostDetailResponse
import com.parksupark.soomjae.features.posts.member.data.model.dto.response.MemberPostResponse
import com.parksupark.soomjae.features.posts.member.data.model.dto.response.PostMemberPostResponse
import com.parksupark.soomjae.features.posts.member.data.model.dto.response.toMemberPost
import com.parksupark.soomjae.features.posts.member.domain.models.MemberPost
import io.ktor.client.HttpClient

internal class RemoteMemberPostDataSource(
    private val httpClient: HttpClient,
) {
    suspend fun createPost(
        content: String,
        imageUrls: List<String>,
    ): Either<DataFailure.Network, PostMemberPostResponse> =
        httpClient.post<PostMemberPostRequest, PostMemberPostResponse>(
            route = "/v1/boards/member/posts",
            body = PostMemberPostRequest(
                content = content,
                imageUrls = imageUrls,
            ),
        )

    suspend fun getMemberPosts(page: Int): Either<DataFailure.Network, List<MemberPost>> =
        httpClient.get<List<MemberPostResponse>>(
            route = "/v1/boards/member/posts/list",
            queryParameters = mapOf("page" to page),
        ).map { response ->
            response.map {
                it.toMemberPost()
            }
        }

    suspend fun getPostDetail(
        postId: Long,
    ): Either<DataFailure.Network, FetchMemberPostDetailResponse> =
        httpClient.get<FetchMemberPostDetailResponse>(
            route = "/v1/boards/member/posts/$postId",
        )
}
