package com.parksupark.soomjae.features.auth.data.repositories

import com.parksupark.soomjae.features.auth.data.datasources.local.LocalLastLoginDataSource
import com.parksupark.soomjae.features.auth.data.datasources.local.toDomain
import com.parksupark.soomjae.features.auth.data.datasources.local.toLocal
import com.parksupark.soomjae.features.auth.domain.model.LoginType
import com.parksupark.soomjae.features.auth.domain.repositories.LastLoginRepository
import kotlinx.coroutines.flow.map

internal class DefaultLastLoginRepository(
    private val localDataSource: LocalLastLoginDataSource,
) : LastLoginRepository {
    override val recentLoginFlow = localDataSource.recentLoginFlow.map { it?.toDomain() }

    override suspend fun saveRecentLogin(loginType: LoginType) {
        localDataSource.saveRecentLogin(loginType.toLocal())
    }
}
