package com.parksupark.soomjae.features.posts.common.data.dtos

import com.parksupark.soomjae.core.domain.models.Member
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// TODO: Add nickname and profileImageUrl fields when backend supports them
@Serializable
data class MemberResponse(
    @SerialName("memberId") val id: Long,
//    val nickname: String,
//    val profileImageUrl: String? = null,
)

// TODO: Remove this when backend supports nickname and profileImageUrl
fun MemberResponse.toModel(): Member = Member(
    id = id.toString(),
    nickname = id.toString(),
    profileImageUrl = "https://picsum.photos/200",
)
