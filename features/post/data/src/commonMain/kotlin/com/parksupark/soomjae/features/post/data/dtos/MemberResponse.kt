package com.parksupark.soomjae.features.post.data.dtos

import com.parksupark.soomjae.features.post.domain.models.Member
import kotlinx.serialization.Serializable

@Serializable
internal data class MemberResponse(
    val id: String,
    val nickname: String,
    val profileImageUrl: String? = null,
)

internal fun MemberResponse.toModel(): Member = Member(
    id = id,
    nickname = nickname,
    profileImageUrl = profileImageUrl,
)
