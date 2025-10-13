package com.parksupark.soomjae.features.profile.data.dtos.response

import com.parksupark.soomjae.features.profile.domain.models.ProfileMemberPost
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class GetProfileMemberPostResponse(
    @SerialName("postId") val id: Long,
    @SerialName("imageUrl") val image: String,
)

internal fun GetProfileMemberPostResponse.toProfileMemberPost(): ProfileMemberPost = ProfileMemberPost(
    id = this.id,
    imageUrl = this.image,
)
