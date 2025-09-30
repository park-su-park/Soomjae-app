package com.parksupark.soomjae.features.posts.common.data.repositories

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.remote.networking.delete
import com.parksupark.soomjae.core.remote.networking.post
import com.parksupark.soomjae.features.posts.common.data.dtos.ParticipationResponse
import com.parksupark.soomjae.features.posts.common.domain.repositories.ParticipationRepository
import io.ktor.client.HttpClient

internal class DefaultParticipationRepository(
    private val httpClient: HttpClient,
) : ParticipationRepository {
    override suspend fun participate(meetingId: Long): Either<DataFailure, Unit> = httpClient.post<Unit, ParticipationResponse>(
        route = "/v1/boards/meeting/posts/$meetingId/join",
        body = Unit,
    ).map {
        // no-op
    }

    override suspend fun cancelParticipation(meetingId: Long): Either<DataFailure, Unit> = httpClient.delete<Unit>(
        route = "/v1/boards/meeting/posts/$meetingId/join",
    ).map {
        // no-op
    }
}
