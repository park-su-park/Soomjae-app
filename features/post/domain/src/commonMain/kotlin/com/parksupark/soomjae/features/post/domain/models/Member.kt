package com.parksupark.soomjae.features.post.domain.models

data class Member(
    val id: String,
    val nickname: String,
    val profileImageUrl: String? = null,
)
