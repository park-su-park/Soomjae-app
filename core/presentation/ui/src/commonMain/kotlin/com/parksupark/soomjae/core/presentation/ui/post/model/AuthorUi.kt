package com.parksupark.soomjae.core.presentation.ui.post.model

import com.parksupark.soomjae.core.domain.models.Member

data class AuthorUi(
    val id: String,
    val nickname: String,
    val profileImageUrl: String,
)

fun Member.toUi(): AuthorUi = AuthorUi(
    id = id,
    nickname = nickname,
    profileImageUrl = profileImageUrl,
)
