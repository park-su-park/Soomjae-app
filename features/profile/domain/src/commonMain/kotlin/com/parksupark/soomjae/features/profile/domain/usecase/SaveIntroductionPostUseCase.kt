package com.parksupark.soomjae.features.profile.domain.usecase

import arrow.core.Either
import com.parksupark.soomjae.core.domain.auth.repositories.SessionRepository
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.features.profile.domain.model.IntroductionPost
import com.parksupark.soomjae.features.profile.domain.repository.IntroductionPostRepository

class SaveIntroductionPostUseCase(
    private val sessionRepository: SessionRepository,
    private val repository: IntroductionPostRepository,
) {
    suspend operator fun invoke(
        current: IntroductionPost?,
        html: String,
    ): Either<DataFailure, IntroductionPost> {
        if (sessionRepository.isLoggedIn()) {
            return Either.Left(DataFailure.Validation.UNAUTHORIZED)
        }

        return if (current == null) {
            repository.create(html)
        } else {
            repository.update(html)
        }
    }
}
