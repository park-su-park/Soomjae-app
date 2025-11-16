package com.parksupark.soomjae.features.posts.member.data.di

import com.parksupark.soomjae.features.posts.common.domain.repositories.CommentRepository
import com.parksupark.soomjae.features.posts.member.data.repository.DefaultMemberPostCommentRepository
import com.parksupark.soomjae.features.posts.member.data.repository.DefaultMemberPostRepository
import com.parksupark.soomjae.features.posts.member.data.source.remote.RemoteMemberPostCommentDataSource
import com.parksupark.soomjae.features.posts.member.data.source.remote.RemoteMemberPostDataSource
import com.parksupark.soomjae.features.posts.member.domain.repositories.MEMBER_COMMENT_REPOSITORY
import com.parksupark.soomjae.features.posts.member.domain.repositories.MemberPostRepository
import io.ktor.client.HttpClient
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Named
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.ksp.generated.module
import org.koin.core.annotation.Module as AnnotationModule

private val postModule = module {
    singleOf(::RemoteMemberPostDataSource)
    singleOf(::DefaultMemberPostRepository).bind<MemberPostRepository>()
}

@AnnotationModule
internal object CommentModule {
    @Single
    fun provideRemoteMemberPostCommentDataSource(
        @Provided httpClient: HttpClient,
    ): RemoteMemberPostCommentDataSource =
        RemoteMemberPostCommentDataSource(httpClient = httpClient)

    @Named(MEMBER_COMMENT_REPOSITORY)
    @Factory
    fun createMemberPostCommentRepository(
        @Provided postDataSource: RemoteMemberPostDataSource,
        commentRemoteSource: RemoteMemberPostCommentDataSource,
    ): CommentRepository = DefaultMemberPostCommentRepository(
        postRemoteSource = postDataSource,
        commentRemoteSource = commentRemoteSource,
    )
}

val featuresPostsMemberDataModule = module {
    includes(postModule, CommentModule.module)
}
