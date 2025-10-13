package com.parksupark.soomjae.features.posts.common.data.repositories

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.remote.networking.delete
import com.parksupark.soomjae.core.remote.networking.get
import com.parksupark.soomjae.core.remote.networking.post
import com.parksupark.soomjae.features.posts.common.data.common.dtos.toModel
import com.parksupark.soomjae.features.posts.common.data.dtos.ParticipantListResponse
import com.parksupark.soomjae.features.posts.common.data.dtos.ParticipationResponse
import com.parksupark.soomjae.features.posts.common.data.dtos.toUpdatedParticipation
import com.parksupark.soomjae.features.posts.common.domain.models.Participant
import com.parksupark.soomjae.features.posts.common.domain.models.UpdatedParticipation
import com.parksupark.soomjae.features.posts.common.domain.repositories.ParticipationRepository
import io.ktor.client.HttpClient

internal class DefaultParticipationRepository(
    private val httpClient: HttpClient,
) : ParticipationRepository {
    override suspend fun participate(meetingId: Long): Either<DataFailure, UpdatedParticipation> =
        httpClient.post<Unit, ParticipationResponse>(
            route = "/v1/boards/meeting/posts/$meetingId/join",
            body = Unit,
        ).map { response ->
            response.toUpdatedParticipation()
        }

    override suspend fun cancelParticipation(meetingId: Long): Either<DataFailure, UpdatedParticipation> = httpClient.delete<Unit>(
        route = "/v1/boards/meeting/posts/$meetingId/join",
    ).map {
        // TODO: Implement proper response handling after API update
        UpdatedParticipation(
            meetingId = meetingId,
            joined = false,
            participantCount = 0,
        )
    }

    override suspend fun getParticipants(meetingId: Long): Either<DataFailure, List<Participant>> = httpClient.get<ParticipantListResponse>(
        route = "/v1/boards/meeting/posts/$meetingId/participants",
    ).map { responses ->
        responses.participants.map { participant ->
            Participant(
                member = participant.toModel(),
            )
        }
    }
}
