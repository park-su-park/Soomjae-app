package com.parksupark.soomjae.features.posts.member.data.di

import com.parksupark.soomjae.features.posts.common.domain.repositories.CommentRepository
import com.parksupark.soomjae.features.posts.member.data.repository.DefaultMemberPostCommentRepository
import com.parksupark.soomjae.features.posts.member.data.repository.DefaultMemberPostRepository
import com.parksupark.soomjae.features.posts.member.data.source.remote.RemoteMemberPostDataSource
import com.parksupark.soomjae.features.posts.member.domain.repositories.MEMBER_COMMENT_REPOSITORY
import com.parksupark.soomjae.features.posts.member.domain.repositories.MemberPostRepository
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

private val postModule = module {
    singleOf(::RemoteMemberPostDataSource)
    singleOf(::DefaultMemberPostRepository).bind<MemberPostRepository>()
}

private val commentModule = module {
    single(named(MEMBER_COMMENT_REPOSITORY)) {
        DefaultMemberPostCommentRepository(get())
    }.bind<CommentRepository>()
}

val featuresPostsMemberDataModule = module {
    includes(postModule, commentModule)
}
