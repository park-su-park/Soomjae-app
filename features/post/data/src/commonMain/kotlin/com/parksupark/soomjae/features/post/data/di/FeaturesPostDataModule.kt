package com.parksupark.soomjae.features.post.data.di

import com.parksupark.soomjae.features.post.data.repositories.CommunityRepositoryImpl
import com.parksupark.soomjae.features.post.data.sources.CommunityRemoteSource
import com.parksupark.soomjae.features.post.data.sources.CommunityRemoteSourceImpl
import com.parksupark.soomjae.features.post.domain.repositories.CommunityRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private val repositoriesModule = module {
    singleOf(::CommunityRepositoryImpl).bind<CommunityRepository>()
}

private val sourcesModule = module {
    singleOf(::CommunityRemoteSourceImpl).bind<CommunityRemoteSource>()
}

val featuresPostDataModule = module {
    includes(repositoriesModule, sourcesModule)
}
