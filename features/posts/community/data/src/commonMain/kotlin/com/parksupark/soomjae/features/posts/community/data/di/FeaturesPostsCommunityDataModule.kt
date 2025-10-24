package com.parksupark.soomjae.features.posts.community.data.di

import com.parksupark.soomjae.features.posts.common.domain.repositories.CommentRepository
import com.parksupark.soomjae.features.posts.common.domain.repositories.LikeRepository
import com.parksupark.soomjae.features.posts.community.data.cache.CommunityPostLikeCache
import com.parksupark.soomjae.features.posts.community.data.remote.source.CommunityLikeRemoteDataSource
import com.parksupark.soomjae.features.posts.community.data.remote.source.CommunityRemoteSource
import com.parksupark.soomjae.features.posts.community.data.remote.source.CommunityRemoteSourceImpl
import com.parksupark.soomjae.features.posts.community.data.repository.CommunityCommentRepository
import com.parksupark.soomjae.features.posts.community.data.repository.CommunityLikeRepository
import com.parksupark.soomjae.features.posts.community.data.repository.DefaultCommunityPostRepository
import com.parksupark.soomjae.features.posts.community.data.repository.OfflineFirstCommunityPostLikeRepository
import com.parksupark.soomjae.features.posts.community.domain.di.PostsCommunityQualifier
import com.parksupark.soomjae.features.posts.community.domain.repository.CommunityPostLikeRepository
import com.parksupark.soomjae.features.posts.community.domain.repository.CommunityPostRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private val postModule = module {
    singleOf(::CommunityRemoteSourceImpl).bind<CommunityRemoteSource>()
    singleOf(::DefaultCommunityPostRepository).bind<CommunityPostRepository>()

    single(PostsCommunityQualifier.COMMENT_REPOSITORY) {
        CommunityCommentRepository(
            httpClient = get(),
        )
    }.bind<CommentRepository>()
}

private val likeModule = module {
    factory { CommunityPostLikeCache() }
    factoryOf(::CommunityLikeRemoteDataSource)
    single(PostsCommunityQualifier.LIKE_REPOSITORY) {
        CommunityLikeRepository(
            likeCache = get(),
            remoteSource = get(),
        )
    }.bind<LikeRepository>()
    singleOf(::OfflineFirstCommunityPostLikeRepository).bind<CommunityPostLikeRepository>()
}

val featuresPostsCommunityDataModule = module {
    includes(postModule, likeModule)
}
