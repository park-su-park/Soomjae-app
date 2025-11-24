package com.parksupark.soomjae.features.profile.data.repository

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.features.profile.data.model.dto.response.toIntroductionPost
import com.parksupark.soomjae.features.profile.data.source.remote.IntroductionPostRemoteDataSource
import com.parksupark.soomjae.features.profile.domain.model.IntroductionPost
import com.parksupark.soomjae.features.profile.domain.repository.IntroductionPostRepository

internal class DefaultIntroductionPostRepository(
    private val remoteSource: IntroductionPostRemoteDataSource,
) : IntroductionPostRepository {
    override suspend fun getByMember(memberId: Long): Either<DataFailure, IntroductionPost?> =
        remoteSource.getByMember(memberId).map {
            it.toIntroductionPost()
        }

    override suspend fun update(html: String): Either<DataFailure, IntroductionPost> =
        remoteSource.put(html).map {
            it.toIntroductionPost()
        }

    override suspend fun delete(memberId: Long): Either<DataFailure, Unit> =
        remoteSource.deleteByMember(memberId)
}
