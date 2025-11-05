package com.parksupark.soomjae.features.auth.domain.model

sealed interface LoginType {
    sealed interface Social : LoginType {
        data object Google : Social

        data object Apple : Social

        data object Kakao : Social

        data object Naver : Social
    }

    sealed interface Credential : LoginType {
        data object Email : Credential
    }
}
