package com.parksupark.soomjae.core.domain.auth.datasources

import com.parksupark.soomjae.core.domain.auth.models.AuthInfo
import kotlinx.coroutines.flow.Flow

interface SessionDataSource {
    suspend fun isLoggedIn(): Boolean

    suspend fun get(): AuthInfo?

    fun getAsFlow(): Flow<AuthInfo?>

    suspend fun set(info: AuthInfo?)

    suspend fun clear()
}
