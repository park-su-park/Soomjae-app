package com.parksupark.soomjae.features.profile.domain.repository

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.features.profile.domain.model.IntroductionPost

interface IntroductionPostRepository {
    suspend fun getByMember(memberId: Long): Either<DataFailure, IntroductionPost?>

    suspend fun create(html: String): Either<DataFailure, IntroductionPost>

    suspend fun update(html: String): Either<DataFailure, IntroductionPost>

    suspend fun delete(memberId: Long): Either<DataFailure, Unit>
}
