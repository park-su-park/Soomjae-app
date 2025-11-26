package com.parksupark.soomjae.features.profile.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
internal data class GetProfileMemberPostsResponse(
    val posts: List<GetProfileMemberPostResponse>,
)
