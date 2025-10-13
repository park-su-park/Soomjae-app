package com.parksupark.soomjae.features.profile.data.dtos.response

import com.parksupark.soomjae.features.profile.domain.models.ProfileMemberPost
import kotlinx.serialization.Serializable

@Serializable
internal data class GetProfileMemberPostsResponse(
    val posts: List<GetProfileMemberPostResponse>,
)
