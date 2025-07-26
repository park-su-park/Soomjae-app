package com.parksupark.soomjae.features.posts.common.data.di

import com.parksupark.soomjae.features.posts.common.data.repositories.CategoryRepositoryImpl
import com.parksupark.soomjae.features.posts.common.data.sources.CategoryRemoteSource
import com.parksupark.soomjae.features.posts.common.data.sources.CategoryRemoteSourceImpl
import com.parksupark.soomjae.features.posts.common.data.sources.RemoteCommunityPostLikeSource
import com.parksupark.soomjae.features.posts.common.data.sources.RemoteCommunityPostLikeSourceImpl
import com.parksupark.soomjae.features.posts.common.domain.repositories.CategoryRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private val sourcesModule = module {
    singleOf(::CategoryRemoteSourceImpl).bind<CategoryRemoteSource>()
    singleOf(::RemoteCommunityPostLikeSourceImpl).bind<RemoteCommunityPostLikeSource>()
}

private val repositoriesModule = module {
    singleOf(::CategoryRepositoryImpl).bind<CategoryRepository>()
}

val featuresPostsCommonDataModule = module {
    includes(sourcesModule, repositoriesModule)
}
