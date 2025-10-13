package com.parksupark.soomjae.features.posts.common.data.common.dtos

import com.parksupark.soomjae.core.domain.models.Member
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// TODO: Add nickname and profileImageUrl fields when backend supports them
@Serializable
data class MemberResponse(
    @SerialName("memberId") val id: Long,
    @SerialName("nickname") val nickname: String?,
    @SerialName("email") val email: String?,
//    val profileImageUrl: String? = null,
)

fun MemberResponse.toModel(): Member = Member(
    id = id.toString(),
    nickname = nickname ?: email ?: "Unknown", // TODO: Use real nickname when backend supports it
    profileImageUrl = "https://picsum.photos/200",
)
