package com.parksupark.soomjae.features.posts.common.presentation.models

import com.parksupark.soomjae.core.domain.models.Member

data class AuthorUi(
    val id: String,
    val nickname: String,
    val profileImageUrl: String?,
)

fun Member.toUi(): AuthorUi = AuthorUi(
    id = id,
    nickname = nickname,
    profileImageUrl = profileImageUrl ?: "https://picsum.photos/seed/${id.toLongOrNull() ?: 0}/200/200",
)
