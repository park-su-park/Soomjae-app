package com.parksupark.soomjae.features.profile.presentation.profile.mdoels

data class UserUi(
    val nickname: String,
    val profileImageUrl: String? = null,
) {
    companion object {
        val Default = UserUi(
            nickname = "Username",
            profileImageUrl = "https://picsum.photos/200",
        )
    }
}
