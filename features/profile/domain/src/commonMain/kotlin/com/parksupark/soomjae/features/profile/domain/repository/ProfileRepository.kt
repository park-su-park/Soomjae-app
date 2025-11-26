package com.parksupark.soomjae.features.profile.domain.repository

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.features.profile.domain.model.Profile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    // Profile
    suspend fun getProfile(memberId: Long): Either<DataFailure, Profile>

    fun getProfileFlow(memberId: Long): Flow<Either<DataFailure, Profile>>

    suspend fun updateProfile(
        newProfile: Profile,
        originalNickname: String,
    ): Either<DataFailure, Profile>

    // Nickname
    suspend fun isNicknameAvailable(nickname: String): Either<DataFailure, Boolean>
}
