package com.parksupark.soomjae.features.posts.member.data.dtos.response

import com.parksupark.soomjae.features.posts.member.domain.models.MemberPost
import kotlinx.serialization.Serializable

// TODO: Add other fields as necessary
@Serializable
internal data class MemberPostResponse(
    val id: Long,
)

internal fun MemberPostResponse.toMemberPost(): MemberPost = MemberPost(
    id = id,
)
