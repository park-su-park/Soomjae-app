package com.parksupark.soomjae.features.posts.member.presentation.post_list.models

import com.parksupark.soomjae.features.posts.member.domain.models.MemberPost

// TODO: Add more fields later
data class MemberPostUi(
    val id: Long,
)

internal fun MemberPost.toMemberPostUi(): MemberPostUi = MemberPostUi(
    id = this.id,
)
