package com.parksupark.soomjae.features.posts.common.data.di

import com.parksupark.soomjae.features.posts.common.data.category.repositories.CategoryRepositoryImpl
import com.parksupark.soomjae.features.posts.common.data.category.sources.CategoryRemoteSource
import com.parksupark.soomjae.features.posts.common.data.category.sources.CategoryRemoteSourceImpl
import com.parksupark.soomjae.features.posts.common.data.like.sources.RemoteCommunityPostLikeSource
import com.parksupark.soomjae.features.posts.common.data.like.sources.RemoteCommunityPostLikeSourceImpl
import com.parksupark.soomjae.features.posts.common.data.location.repositories.DefaultLocationRepository
import com.parksupark.soomjae.features.posts.common.data.location.sources.DefaultRemoteLocationSourceImpl
import com.parksupark.soomjae.features.posts.common.data.location.sources.RemoteLocationSource
import com.parksupark.soomjae.features.posts.common.domain.repositories.CategoryRepository
import com.parksupark.soomjae.features.posts.common.domain.repositories.LocationRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private val categoryModule = module {
    singleOf(::CategoryRemoteSourceImpl).bind<CategoryRemoteSource>()
    singleOf(::CategoryRepositoryImpl).bind<CategoryRepository>()
}

private val likeModule = module {
    singleOf(::RemoteCommunityPostLikeSourceImpl).bind<RemoteCommunityPostLikeSource>()
}

private val locationModule = module {
    singleOf(::DefaultRemoteLocationSourceImpl).bind<RemoteLocationSource>()
    singleOf(::DefaultLocationRepository).bind<LocationRepository>()
}

val featuresPostsCommonDataModule = module {
    includes(categoryModule, likeModule, locationModule)
}
