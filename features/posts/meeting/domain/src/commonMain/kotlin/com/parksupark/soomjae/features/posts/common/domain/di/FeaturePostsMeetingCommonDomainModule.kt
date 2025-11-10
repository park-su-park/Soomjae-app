package com.parksupark.soomjae.features.posts.common.domain.di

import com.parksupark.soomjae.features.posts.common.domain.usecase.ValidatePeriodUseCase
import org.koin.core.annotation.Factory
import org.koin.dsl.module
import org.koin.ksp.generated.module
import org.koin.core.annotation.Module as AnnotationModule

@AnnotationModule
internal object UseCaseModule {
    @Factory
    fun createValidatePeriodUseCase(): ValidatePeriodUseCase = ValidatePeriodUseCase()
}

val featurePostsMeetingCommonDomainModule = module {
    includes(UseCaseModule.module)
}
