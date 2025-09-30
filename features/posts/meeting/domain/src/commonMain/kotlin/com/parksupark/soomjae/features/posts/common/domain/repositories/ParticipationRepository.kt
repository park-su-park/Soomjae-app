package com.parksupark.soomjae.features.posts.common.domain.repositories

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure

interface ParticipationRepository {
    suspend fun participate(meetingId: Long): Either<DataFailure, Unit>

    suspend fun cancelParticipation(meetingId: Long): Either<DataFailure, Unit>
}
