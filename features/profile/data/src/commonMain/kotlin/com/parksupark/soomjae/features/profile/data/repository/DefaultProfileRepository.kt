package com.parksupark.soomjae.features.profile.data.repository

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.features.profile.data.model.mapper.toProfile
import com.parksupark.soomjae.features.profile.data.model.mapper.toPutProfileRequest
import com.parksupark.soomjae.features.profile.data.source.cache.ProfileCacheDataSource
import com.parksupark.soomjae.features.profile.data.source.remote.ProfileRemoteDataSource
import com.parksupark.soomjae.features.profile.domain.model.Profile
import com.parksupark.soomjae.features.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class DefaultProfileRepository(
    private val cacheSource: ProfileCacheDataSource,
    private val remoteSource: ProfileRemoteDataSource,
) : ProfileRepository {
    override suspend fun getProfile(memberId: Long): Either<DataFailure, Profile> {
        return remoteSource.fetchProfile(memberId).map {
            it.toProfile()
        }
    }

    override fun getProfileFlow(memberId: Long): Flow<Either<DataFailure, Profile>> = flow {
        val cachedProfile = cacheSource.getProfile(memberId)
        if (cachedProfile != null) {
            emit(Either.Right(cachedProfile))
        }

        val remoteResult = remoteSource.fetchProfile(memberId)
        remoteResult.map { response ->
            response.toProfile()
        }.onRight { remoteProfile ->
            if (remoteProfile != cachedProfile) {
                cacheSource.upsertProfile(remoteProfile)
                emit(Either.Right(remoteProfile))
            }
        }
    }

    override suspend fun updateProfile(newProfile: Profile): Either<DataFailure, Profile> {
        return remoteSource.putProfile(newProfile.toPutProfileRequest()).map { response ->
            val updatedProfile = response.toProfile()
            cacheSource.upsertProfile(updatedProfile)
            updatedProfile
        }
    }

    override suspend fun checkAvailableNickname(nickname: String): Either<DataFailure, Boolean> {
        // TODO: "Not yet implemented"
        return Either.Left(DataFailure.Local.UNKNOWN)
    }
}
