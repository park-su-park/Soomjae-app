package com.parksupark.soomjae.features.profile.domain.di

import com.parksupark.soomjae.features.profile.domain.repository.ProfileRepository
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
}

val featureProfileDomainModule = module {
    includes(UseCaseModule.module)
}
