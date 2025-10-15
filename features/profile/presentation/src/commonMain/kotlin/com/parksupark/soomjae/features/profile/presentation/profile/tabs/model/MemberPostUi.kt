package com.parksupark.soomjae.features.profile.presentation.profile.tabs.model

import com.parksupark.soomjae.features.profile.domain.models.ProfileMemberPost

data class MemberPostUi(
    val id: Long,
    val imageUrl: String,
)

internal fun ProfileMemberPost.toMemberPostUi(): MemberPostUi = MemberPostUi(
    id = this.id,
    imageUrl = this.imageUrl,
)
