package com.parksupark.soomjae.features.auth.domain.repositories

import com.parksupark.soomjae.features.auth.domain.model.LoginType
import kotlinx.coroutines.flow.Flow

interface LastLoginRepository {
    val recentLoginFlow: Flow<LoginType?>

    suspend fun saveRecentLogin(loginType: LoginType)
}
