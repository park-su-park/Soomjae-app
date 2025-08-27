package com.parksupark.soomjae.features.auth.libs.google.authenticators

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.features.auth.libs.google.models.GoogleUser

interface GoogleAuthUi {
    suspend fun getUser(): Either<DataFailure.Credential, GoogleUser>
}
