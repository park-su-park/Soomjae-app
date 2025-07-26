package com.parksupark.soomjae.features.posts.community.data.di

import com.parksupark.soomjae.features.posts.community.data.repositories.CommunityRepositoryImpl
import com.parksupark.soomjae.features.posts.community.data.repositories.OfflineFirstCommunityPostLikeRepository
import com.parksupark.soomjae.features.posts.community.data.sources.CommunityRemoteSource
import com.parksupark.soomjae.features.posts.community.data.sources.CommunityRemoteSourceImpl
import com.parksupark.soomjae.features.posts.community.domain.repositories.CommunityPostLikeRepository
import com.parksupark.soomjae.features.posts.community.domain.repositories.CommunityRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private val repositoriesModule = module {
    singleOf(::OfflineFirstCommunityPostLikeRepository).bind<CommunityPostLikeRepository>()
    singleOf(::CommunityRepositoryImpl).bind<CommunityRepository>()
}

private val sourcesModule = module {
    singleOf(::CommunityRemoteSourceImpl).bind<CommunityRemoteSource>()
}

val featuresPostsCommunityDataModule = module {
    includes(repositoriesModule, sourcesModule)
}
