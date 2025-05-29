package com.parksupark.soomjae.core.domain.auth.repositories

import com.parksupark.soomjae.core.domain.auth.models.AuthInfo

interface SessionRepository {
    suspend fun get(): AuthInfo?

    suspend fun set(info: AuthInfo?)
}
