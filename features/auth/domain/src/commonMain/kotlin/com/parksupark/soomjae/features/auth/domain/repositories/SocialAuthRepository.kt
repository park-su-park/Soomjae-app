package com.parksupark.soomjae.features.auth.domain.repositories

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure

interface SocialAuthRepository {
    suspend fun signInWithGoogle(idToken: String): Either<DataFailure, Unit>
}
