package com.parksupark.soomjae.features.posts.aggregate.domain.di

import com.parksupark.soomjae.features.posts.common.domain.di.featurePostsMeetingCommonDomainModule
import org.koin.dsl.module

val featuresPostsAggregateDomainModule = module {
    includes(featurePostsMeetingCommonDomainModule)
}
