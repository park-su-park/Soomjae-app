package com.parksupark.soomjae.features.auth.libs.google.di.authenticators

interface GoogleAuthService {
    fun signInWithScope(
        scope: List<String>,
        onComplete: (
            idToken: String?,
            accessToken: String?,
            email: String?,
            name: String?,
            photoUrl: String?,
            serverAuthCode: String?,
            error: String?,
        ) -> Unit,
    )
}
