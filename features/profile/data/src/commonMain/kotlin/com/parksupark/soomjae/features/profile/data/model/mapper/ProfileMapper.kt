package com.parksupark.soomjae.features.profile.data.model.mapper

import com.parksupark.soomjae.features.profile.data.model.dto.request.PutProfileRequest
import com.parksupark.soomjae.features.profile.data.model.dto.response.ProfileResponse
import com.parksupark.soomjae.features.profile.domain.model.Profile

internal fun ProfileResponse.toProfile() = Profile(
    memberId = this.memberId,
    nickname = this.nickname,
    bio = this.bio,
    profileImageUrl = this.profileImageUrl
)

internal fun Profile.toPutProfileRequest(originalNickname: String) = PutProfileRequest(
    nickname = if (this.nickname == originalNickname) null else this.nickname,
    bio = this.bio,
    profileImageUrl = this.profileImageUrl
)
