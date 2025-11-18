package com.parksupark.soomjae.features.profile.data.model.mapper

import com.parksupark.soomjae.features.profile.data.model.dto.response.FetchProfileResponse
import com.parksupark.soomjae.features.profile.domain.model.Profile

internal fun FetchProfileResponse.toProfile() = Profile(
    memberId = this.memberId,
    nickname = this.nickname,
    bio = this.bio,
    profileImageUrl = this.profileImageUrl
)
