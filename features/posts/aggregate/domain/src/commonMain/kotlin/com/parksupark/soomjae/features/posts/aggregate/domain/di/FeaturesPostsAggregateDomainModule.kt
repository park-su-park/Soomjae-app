package com.parksupark.soomjae.features.posts.aggregate.domain.di

import com.parksupark.soomjae.features.posts.common.domain.di.featurePostsMeetingCommonDomainModule
import com.parksupark.soomjae.features.posts.member.domain.di.featuresPostsMemberDomainModule
import org.koin.dsl.module

val featuresPostsAggregateDomainModule = module {
    includes(featurePostsMeetingCommonDomainModule)
    includes(featuresPostsMemberDomainModule)
}
