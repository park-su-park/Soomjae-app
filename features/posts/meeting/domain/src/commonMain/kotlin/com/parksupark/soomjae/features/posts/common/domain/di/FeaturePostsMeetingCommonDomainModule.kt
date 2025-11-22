package com.parksupark.soomjae.features.posts.common.domain.di

import com.parksupark.soomjae.core.domain.auth.repositories.SessionRepository
import com.parksupark.soomjae.features.posts.common.domain.event.MeetingPostEventBus
import com.parksupark.soomjae.features.posts.common.domain.repositories.MeetingPostRepository
import com.parksupark.soomjae.features.posts.common.domain.usecase.GetMeetingPostForEditUseCase
import com.parksupark.soomjae.features.posts.common.domain.usecase.UpdateMeetingPostUseCase
import com.parksupark.soomjae.features.posts.common.domain.usecase.ValidatePeriodUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single
import org.koin.dsl.module
import org.koin.ksp.generated.module
import org.koin.core.annotation.Module as AnnotationModule

@AnnotationModule
internal object UseCaseModule {
    @Factory
    fun createValidatePeriodUseCase(): ValidatePeriodUseCase = ValidatePeriodUseCase()

    @Factory
    fun createGetMeetingPostForEditUseCase(
        @Provided meetingPostRepository: MeetingPostRepository,
    ): GetMeetingPostForEditUseCase = GetMeetingPostForEditUseCase(
        meetingPostRepository = meetingPostRepository,
    )

    @Factory
    fun createUpdateMeetingPostUseCase(
        @Provided meetingPostRepository: MeetingPostRepository,
        @Provided sessionRepository: SessionRepository,
    ): UpdateMeetingPostUseCase = UpdateMeetingPostUseCase(
        sessionRepository = sessionRepository,
        postRepository = meetingPostRepository,
    )

    @Single
    fun provideMeetingPostEventBus(): MeetingPostEventBus = MeetingPostEventBus()
}

val featurePostsMeetingCommonDomainModule = module {
    includes(UseCaseModule.module)
}
