package com.parksupark.soomjae.features.post.data.di

import com.parksupark.soomjae.features.post.data.repositories.CategoryRepositoryImpl
import com.parksupark.soomjae.features.post.data.repositories.CommunityRepositoryImpl
import com.parksupark.soomjae.features.post.data.repositories.OfflineFirstCommunityPostLikeRepository
import com.parksupark.soomjae.features.post.data.sources.CategoryRemoteSource
import com.parksupark.soomjae.features.post.data.sources.CategoryRemoteSourceImpl
import com.parksupark.soomjae.features.post.data.sources.CommunityRemoteSource
import com.parksupark.soomjae.features.post.data.sources.CommunityRemoteSourceImpl
import com.parksupark.soomjae.features.post.data.sources.RemoteCommunityPostLikeSource
import com.parksupark.soomjae.features.post.data.sources.RemoteCommunityPostLikeSourceImpl
import com.parksupark.soomjae.features.post.domain.repositories.CategoryRepository
import com.parksupark.soomjae.features.post.domain.repositories.CommunityPostLikeRepository
import com.parksupark.soomjae.features.post.domain.repositories.CommunityRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private val repositoriesModule = module {
    singleOf(::CategoryRepositoryImpl).bind<CategoryRepository>()
    singleOf(::CommunityRepositoryImpl).bind<CommunityRepository>()
    singleOf(::OfflineFirstCommunityPostLikeRepository).bind<CommunityPostLikeRepository>()
}

private val sourcesModule = module {
    singleOf(::CategoryRemoteSourceImpl).bind<CategoryRemoteSource>()
    singleOf(::CommunityRemoteSourceImpl).bind<CommunityRemoteSource>()
    singleOf(::RemoteCommunityPostLikeSourceImpl).bind<RemoteCommunityPostLikeSource>()
}

val featuresPostDataModule = module {
    includes(repositoriesModule, sourcesModule)
}
