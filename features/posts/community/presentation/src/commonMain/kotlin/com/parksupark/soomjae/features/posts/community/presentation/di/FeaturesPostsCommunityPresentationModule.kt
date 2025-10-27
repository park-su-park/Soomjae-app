package com.parksupark.soomjae.features.posts.community.presentation.di

import com.parksupark.soomjae.features.posts.community.domain.di.PostsCommunityQualifier
import com.parksupark.soomjae.features.posts.community.domain.usecase.GetCommunityPostDetailWithLikedStream
import com.parksupark.soomjae.features.posts.community.presentation.detail.CommunityDetailViewModel
import com.parksupark.soomjae.features.posts.community.presentation.tab.post.CommunityTabPostViewModel
import com.parksupark.soomjae.features.posts.community.presentation.write.CommunityWriteViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

private val tabModule = module {
    viewModel {
        CommunityTabPostViewModel(
            postRepository = get(),
            sessionRepository = get(),
            soomjaeEventController = get(),
        )
    }
}

private val detailModule = module {
    singleOf(::GetCommunityPostDetailWithLikedStream)
    viewModel {
        CommunityDetailViewModel(
            savedStateHandle = get(),
            dispatcher = get(),
            postRepository = get(),
            sessionRepository = get(),
            commentRepository = get(PostsCommunityQualifier.COMMENT_REPOSITORY),
            likeRepository = get(PostsCommunityQualifier.LIKE_REPOSITORY),
            soomjaeEventController = get(),
        )
    }
}

private val writeModule = module {
    viewModelOf(::CommunityWriteViewModel)
}

val featuresPostsCommunityPresentationModule = module {
    includes(tabModule, detailModule, writeModule)
}
