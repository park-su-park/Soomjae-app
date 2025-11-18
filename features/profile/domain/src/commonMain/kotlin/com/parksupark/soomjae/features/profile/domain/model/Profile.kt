package com.parksupark.soomjae.features.profile.domain.model

data class Profile(
    val memberId: Long,
    val nickname: String,
    val bio: String,
    val profileImageUrl: String?,
)
