package com.parksupark.soomjae.features.posts.community.presentation.di

import com.parksupark.soomjae.features.posts.community.domain.usecases.GetCommunityPostDetailWithLikedStream
import com.parksupark.soomjae.features.posts.community.presentation.detail.CommunityDetailViewModel
import com.parksupark.soomjae.features.posts.community.presentation.tab.CommunityTabViewModel
import com.parksupark.soomjae.features.posts.community.presentation.write.CommunityWriteViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

private val tabModule = module {
    viewModelOf(::CommunityTabViewModel)
}

private val detailModule = module {
    singleOf(::GetCommunityPostDetailWithLikedStream)
    viewModelOf(::CommunityDetailViewModel)
}

private val writeModule = module {
    viewModelOf(::CommunityWriteViewModel)
}

val featuresPostsCommunityPresentationModule = module {
    includes(tabModule, detailModule, writeModule)
}
