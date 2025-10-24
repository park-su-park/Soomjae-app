package com.parksupark.soomjae.features.posts.community.data.di

import com.parksupark.soomjae.features.posts.common.domain.repositories.CommentRepository
import com.parksupark.soomjae.features.posts.common.domain.repositories.LikeRepository
import com.parksupark.soomjae.features.posts.community.data.remote.source.CommunityRemoteSource
import com.parksupark.soomjae.features.posts.community.data.remote.source.CommunityRemoteSourceImpl
import com.parksupark.soomjae.features.posts.community.data.repository.CommunityCommentRepository
import com.parksupark.soomjae.features.posts.community.data.repository.CommunityLikeRepository
import com.parksupark.soomjae.features.posts.community.data.repository.DefaultCommunityPostRepository
import com.parksupark.soomjae.features.posts.community.data.repository.OfflineFirstCommunityPostLikeRepository
import com.parksupark.soomjae.features.posts.community.domain.repository.COMMUNITY_COMMENT_REPOSITORY
import com.parksupark.soomjae.features.posts.community.domain.repository.COMMUNITY_LIKE_REPOSITORY
import com.parksupark.soomjae.features.posts.community.domain.repository.CommunityPostLikeRepository
import com.parksupark.soomjae.features.posts.community.domain.repository.CommunityPostRepository
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

private val repositoriesModule = module {
    singleOf(::OfflineFirstCommunityPostLikeRepository).bind<CommunityPostLikeRepository>()
    singleOf(::DefaultCommunityPostRepository).bind<CommunityPostRepository>()
    single(named(COMMUNITY_COMMENT_REPOSITORY)) {
        CommunityCommentRepository(
            httpClient = get(),
        )
    }.bind<CommentRepository>()
    single(named(COMMUNITY_LIKE_REPOSITORY)) {
        CommunityLikeRepository(
            httpClient = get(),
        )
    }.bind<LikeRepository>()
}

private val sourcesModule = module {
    singleOf(::CommunityRemoteSourceImpl).bind<CommunityRemoteSource>()
}

val featuresPostsCommunityDataModule = module {
    includes(repositoriesModule, sourcesModule)
}
