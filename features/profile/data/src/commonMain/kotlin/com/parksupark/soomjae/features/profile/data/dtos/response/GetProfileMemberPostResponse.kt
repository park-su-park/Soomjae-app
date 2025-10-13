package com.parksupark.soomjae.features.profile.data.dtos.response

import com.parksupark.soomjae.features.profile.domain.models.ProfileMemberPost
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class GetProfileMemberPostResponse(
    @SerialName("memberPostId") val postId: Long,
    @SerialName("image") val imageUrl: String,
)

internal fun GetProfileMemberPostResponse.toProfileMemberPost(): ProfileMemberPost = ProfileMemberPost(
    id = this.postId,
    imageUrl = this.imageUrl,
)
