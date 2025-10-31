package com.parksupark.soomjae.features.profile.domain.model

data class Profile(
    val memberId: Long,
    val nickname: String,
    val profileImageUrl: String?,
    val bio: String,
)
