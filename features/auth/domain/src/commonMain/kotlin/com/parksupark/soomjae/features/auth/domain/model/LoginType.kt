package com.parksupark.soomjae.features.auth.domain.model

sealed interface LoginType {
    sealed interface Social : LoginType {
        object Google : Social

        object Apple : Social

        object Kakao : Social

        object Naver : Social
    }

    sealed interface Credential : LoginType {
        object Email : Credential
    }
}
