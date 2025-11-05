package com.parksupark.soomjae.features.auth.data.datasources.local

import com.parksupark.soomjae.features.auth.domain.model.LoginType

internal enum class LoginTypeLocal {
    GOOGLE,
    APPLE,
    KAKAO,
    NAVER,
    EMAIL,
}

internal fun LoginTypeLocal.toDomain(): LoginType = when (this) {
    LoginTypeLocal.GOOGLE -> LoginType.Social.Google
    LoginTypeLocal.APPLE -> LoginType.Social.Apple
    LoginTypeLocal.KAKAO -> LoginType.Social.Kakao
    LoginTypeLocal.NAVER -> LoginType.Social.Naver
    LoginTypeLocal.EMAIL -> LoginType.Credential.Email
}

internal fun LoginType.toLocal(): LoginTypeLocal = when (this) {
    is LoginType.Social.Google -> LoginTypeLocal.GOOGLE
    is LoginType.Social.Apple -> LoginTypeLocal.APPLE
    is LoginType.Social.Kakao -> LoginTypeLocal.KAKAO
    is LoginType.Social.Naver -> LoginTypeLocal.NAVER
    is LoginType.Credential.Email -> LoginTypeLocal.EMAIL
}
