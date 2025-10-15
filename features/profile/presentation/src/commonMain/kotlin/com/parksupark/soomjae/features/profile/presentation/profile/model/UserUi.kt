package com.parksupark.soomjae.features.profile.presentation.profile.model

data class UserUi(
    val id: Long,
    val nickname: String,
    val profileImageUrl: String? = "https://picsum.photos/id/$id",
) {
    companion object {
        val Default = UserUi(
            id = 1,
            nickname = "Username",
            profileImageUrl = "https://picsum.photos/id/1",
        )
    }
}
