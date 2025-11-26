package com.parksupark.soomjae.features.posts.common.data.common.dtos

import com.parksupark.soomjae.core.domain.models.Member
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MemberResponse(
    @SerialName("memberId") val id: Long,
    @SerialName("nickname") val nickname: String?,
    @SerialName("email") val email: String?,
    @SerialName("profileImageUrl") val profileImageUrl: String? = null,
)

fun MemberResponse.toModel(): Member = Member(
    id = this.id.toString(),
    nickname = this.nickname ?: this.email ?: "Unknown",
    profileImageUrl = this.profileImageUrl
        ?: "https://picsum.photos/seed/${id.toString() + nickname}/200/200", // TODO: Use real profileImageUrl when backend supports it
)
