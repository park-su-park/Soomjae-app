package com.parksupark.soomjae.features.auth.libs.google.models

data class GoogleUser(
    val idToken: String,
    val accessToken: String? = null,
    val email: String? = null,
    val displayName: String = "",
    val profilePicUrl: String? = null,
    val serverAuthCode: String? = null,
)
