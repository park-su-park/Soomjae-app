package com.parksupark.soomjae.features.posts.member.data.dtos.response

import kotlinx.serialization.Serializable

// TODO: Add other fields as necessary
@Serializable
internal data class MemberPostsResponse(
    val posts: List<MemberPostResponse>,
)
