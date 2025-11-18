package com.parksupark.soomjae.features.profile.domain.usecase

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.features.profile.domain.model.NicknameValidationResult
import com.parksupark.soomjae.features.profile.domain.model.NicknameValidationResult.Invalid.Reason.AlreadyTaken
import com.parksupark.soomjae.features.profile.domain.model.NicknameValidationResult.Invalid.Reason.InvalidFormat
import com.parksupark.soomjae.features.profile.domain.repository.ProfileRepository

class ValidateNicknameUseCase(
    private val profileRepository: ProfileRepository,
) {
    suspend operator fun invoke(nickname: String): Either<DataFailure, NicknameValidationResult> {
        if (nickname.isEmpty()) {
            return Either.Right(NicknameValidationResult.Invalid(InvalidFormat))
        }

        return profileRepository.isNicknameAvailable(nickname).map { result ->
            if (result) {
                NicknameValidationResult.Valid
            } else {
                NicknameValidationResult.Invalid(AlreadyTaken)
            }
        }
    }
}
