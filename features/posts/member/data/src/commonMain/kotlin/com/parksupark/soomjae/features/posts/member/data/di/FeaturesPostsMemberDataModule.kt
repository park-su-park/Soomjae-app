package com.parksupark.soomjae.features.posts.member.data.di

import com.parksupark.soomjae.features.posts.member.data.datasources.remote.RemoteMemberPostDataSource
import com.parksupark.soomjae.features.posts.member.data.repositories.DefaultMemberPostRepository
import com.parksupark.soomjae.features.posts.member.domain.repositories.MemberPostRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private val postModule = module {
    singleOf(::RemoteMemberPostDataSource)
    singleOf(::DefaultMemberPostRepository).bind<MemberPostRepository>()
}

val featuresPostsMemberDataModule = module {
    includes(postModule)
}
