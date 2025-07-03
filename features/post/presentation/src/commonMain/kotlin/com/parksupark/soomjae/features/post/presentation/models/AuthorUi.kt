package com.parksupark.soomjae.features.post.presentation.models

import com.parksupark.soomjae.features.post.domain.models.Member

internal data class AuthorUi(
    val id: String,
    val nickname: String,
    val profileImageUrl: String? = null,
)

internal fun Member.toUi(): AuthorUi = AuthorUi(
    id = id,
    nickname = nickname,
    profileImageUrl = profileImageUrl,
)
