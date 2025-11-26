package com.parksupark.soomjae.features.posts.member.domain.di

import com.parksupark.soomjae.core.domain.auth.repositories.SessionRepository
import com.parksupark.soomjae.features.posts.common.domain.repositories.CommentRepository
import com.parksupark.soomjae.features.posts.member.domain.repositories.MEMBER_COMMENT_REPOSITORY
import com.parksupark.soomjae.features.posts.member.domain.usecase.CreateMemberPostCommentUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Named
import org.koin.core.annotation.Provided
import org.koin.dsl.module
import org.koin.ksp.generated.module
import org.koin.core.annotation.Module as AnnotationModule

@AnnotationModule
internal object UseCaseModule {
    @Factory
    fun createCreateMemberPostCommentUseCase(
        @Provided sessionRepository: SessionRepository,
        @Provided @Named(MEMBER_COMMENT_REPOSITORY) commentRepository: CommentRepository,
    ): CreateMemberPostCommentUseCase = CreateMemberPostCommentUseCase(
        sessionRepository = sessionRepository,
        commentRepository = commentRepository,
    )
}

val featuresPostsMemberDomainModule = module {
    includes(UseCaseModule.module)
}
