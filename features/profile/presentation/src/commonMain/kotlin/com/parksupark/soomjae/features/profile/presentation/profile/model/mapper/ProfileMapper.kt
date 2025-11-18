package com.parksupark.soomjae.features.profile.presentation.profile.model.mapper

import com.parksupark.soomjae.features.profile.domain.model.Profile
import com.parksupark.soomjae.features.profile.presentation.profile.model.UserUi

internal fun Profile.toUserUi(): UserUi {
    return UserUi(
        id = this.memberId,
        nickname = this.nickname,
        bio = this.bio,
        profileImageUrl = this.profileImageUrl,
    )
}
