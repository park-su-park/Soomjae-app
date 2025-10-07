package com.parksupark.soomjae.features.posts.member.presentation.di

import com.parksupark.soomjae.features.posts.member.presentation.post_list.MemberPostListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

private val memberPostListModule = module {
    viewModelOf(::MemberPostListViewModel)
}

val featuresPostsMemberPresentationModule = module {
    includes(memberPostListModule)
}
