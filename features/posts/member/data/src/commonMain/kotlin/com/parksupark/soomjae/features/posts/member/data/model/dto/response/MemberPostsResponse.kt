package com.parksupark.soomjae.features.posts.member.data.model.dto.response

import kotlinx.serialization.Serializable

// TODO: Add other fields as necessary
@Serializable
internal data class MemberPostsResponse(
    val posts: List<MemberPostResponse>,
)
