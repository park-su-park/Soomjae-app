package com.parksupark.soomjae.features.profile.domain.usecase

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.features.profile.domain.model.IntroductionPost
import com.parksupark.soomjae.features.profile.domain.repository.IntroductionPostRepository

class GetIntroductionUseCase(
    private val repository: IntroductionPostRepository,
) {
    suspend operator fun invoke(memberId: Long): Either<DataFailure, IntroductionPost?> =
        repository.getByMember(memberId)
}
