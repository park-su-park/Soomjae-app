package com.parksupark.soomjae.features.profile.domain.di

import com.parksupark.soomjae.core.domain.auth.repositories.SessionRepository
import com.parksupark.soomjae.features.profile.domain.repository.IntroductionPostRepository
import com.parksupark.soomjae.features.profile.domain.repository.ProfileRepository
import com.parksupark.soomjae.features.profile.domain.usecase.GetIntroductionUseCase
import com.parksupark.soomjae.features.profile.domain.usecase.SaveIntroductionPostUseCase
import com.parksupark.soomjae.features.profile.domain.usecase.ValidateNicknameUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Provided
import org.koin.dsl.module
import org.koin.ksp.generated.module

@Module
internal object UseCaseModule {
    @Factory
    fun createValidateNicknameUseCase(
        @Provided profileRepository: ProfileRepository,
    ): ValidateNicknameUseCase = ValidateNicknameUseCase(
        profileRepository = profileRepository,
    )

    @Factory
    fun createGetIntroductionUseCase(
        @Provided introductionPostRepository: IntroductionPostRepository,
    ): GetIntroductionUseCase = GetIntroductionUseCase(
        repository = introductionPostRepository,
    )

    @Factory
    fun createSaveIntroductionPostUseCase(
        @Provided sessionRepository: SessionRepository,
        @Provided introductionPostRepository: IntroductionPostRepository,
    ): SaveIntroductionPostUseCase = SaveIntroductionPostUseCase(
        sessionRepository = sessionRepository,
        repository = introductionPostRepository,
    )
}

val featureProfileDomainModule = module {
    includes(UseCaseModule.module)
}
