package com.parksupark.soomjae.features.posts.common.domain.repositories

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.features.posts.common.domain.models.Participant
import com.parksupark.soomjae.features.posts.common.domain.models.UpdatedParticipation

interface ParticipationRepository {
    suspend fun participate(meetingId: Long): Either<DataFailure, UpdatedParticipation>

    suspend fun deleteParticipation(meetingId: Long): Either<DataFailure, UpdatedParticipation>

    suspend fun getParticipants(meetingId: Long): Either<DataFailure, List<Participant>>
}
