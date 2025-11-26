package com.parksupark.soomjae.core.data.auth.repositories

import com.parksupark.soomjae.core.domain.auth.datasources.SessionDataSource
import com.parksupark.soomjae.core.domain.auth.models.AuthInfo
import com.parksupark.soomjae.core.domain.auth.repositories.SessionRepository
import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.authProviders
import io.ktor.client.plugins.auth.providers.BearerAuthProvider
import kotlinx.coroutines.flow.Flow

internal class SessionRepositoryImpl(
    private val sessionDataSource: SessionDataSource,
    private val httpClient: HttpClient,
) : SessionRepository {
    override suspend fun isLoggedIn(): Boolean = sessionDataSource.isLoggedIn()

    override suspend fun get(): AuthInfo? = sessionDataSource.get()

    override fun getAsFlow(): Flow<AuthInfo?> = sessionDataSource.getAsFlow()

    override suspend fun set(info: AuthInfo?) {
        sessionDataSource.set(info)
        httpClient.authProviders.filterIsInstance<BearerAuthProvider>().onEach {
            it.clearToken()
        }
    }

    override suspend fun getCurrentUserId(): Long? = this.get()?.memberId
}
