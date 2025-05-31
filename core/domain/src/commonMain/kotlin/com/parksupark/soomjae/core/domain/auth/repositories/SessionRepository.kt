package com.parksupark.soomjae.core.domain.auth.repositories

import com.parksupark.soomjae.core.domain.auth.models.AuthInfo
import kotlinx.coroutines.flow.Flow

interface SessionRepository {
    suspend fun get(): AuthInfo?

    fun getAsFlow(): Flow<AuthInfo?>

    suspend fun set(info: AuthInfo?)
}
