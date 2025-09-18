package com.parksupark.soomjae.features.posts.community.presentation.di

import com.parksupark.soomjae.features.posts.community.domain.repositories.COMMUNITY_COMMENT_REPOSITORY
import com.parksupark.soomjae.features.posts.community.domain.usecases.GetCommunityPostDetailWithLikedStream
import com.parksupark.soomjae.features.posts.community.presentation.detail.CommunityDetailViewModel
import com.parksupark.soomjae.features.posts.community.presentation.tab.CommunityTabViewModel
import com.parksupark.soomjae.features.posts.community.presentation.write.CommunityWriteViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

private val tabModule = module {
    viewModelOf(::CommunityTabViewModel)
}

private val detailModule = module {
    singleOf(::GetCommunityPostDetailWithLikedStream)
    viewModel {
        CommunityDetailViewModel(
            savedStateHandle = get(),
            getPostWithLikedStream = get(),
            commentRepository = get(named(COMMUNITY_COMMENT_REPOSITORY)),
        )
    }
}

private val writeModule = module {
    viewModelOf(::CommunityWriteViewModel)
}

val featuresPostsCommunityPresentationModule = module {
    includes(tabModule, detailModule, writeModule)
}
