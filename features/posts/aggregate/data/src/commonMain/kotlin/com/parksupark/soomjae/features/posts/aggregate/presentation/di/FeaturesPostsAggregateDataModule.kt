package com.parksupark.soomjae.features.posts.aggregate.presentation.di

import com.parksupark.soomjae.features.posts.common.data.di.featuresPostsCommonDataModule
import com.parksupark.soomjae.features.posts.common.data.di.featuresPostsMeetingDataModule
import com.parksupark.soomjae.features.posts.community.data.di.featuresPostsCommunityDataModule
import com.parksupark.soomjae.features.posts.member.data.di.featuresPostsMemberDataModule
import org.koin.dsl.module

private val repositoriesModule = module { }

private val sourcesModule = module { }

val featuresPostsAggregateDataModule = module {
    includes(repositoriesModule, sourcesModule)
    includes(featuresPostsCommonDataModule)
    includes(featuresPostsCommunityDataModule)
    includes(featuresPostsMeetingDataModule)
    includes(featuresPostsMemberDataModule)
}
