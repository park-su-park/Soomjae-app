package com.parksupark.soomjae.di

import com.parksupark.soomjae.core.analytics.di.coreAnalyticsModule
import com.parksupark.soomjae.core.common.di.coreCommonModule
import com.parksupark.soomjae.core.data.di.coreDataModule
import com.parksupark.soomjae.core.image.di.coreImageModule
import com.parksupark.soomjae.core.presentation.ui.di.corePresentationUiModule
import com.parksupark.soomjae.core.remote.di.coreRemoteModule
import com.parksupark.soomjae.features.auth.data.di.featuresAuthDataModule
import com.parksupark.soomjae.features.auth.presentation.di.featuresAuthPresentationModule
import com.parksupark.soomjae.features.home.presentation.di.featuresHomePresentationModule
import com.parksupark.soomjae.features.posts.aggregate.presentation.di.featuresPostsAggregateDataModule
import com.parksupark.soomjae.features.posts.aggregate.presentation.di.featuresPostsAggregatePresentationModule
import com.parksupark.soomjae.features.profile.data.di.featuresProfileDataModule
import com.parksupark.soomjae.features.profile.presentation.di.featuresProfilePresentationModule
import com.parksupark.soomjae.features.setting.presentation.di.featuresSettingPresentationModule
import com.parksupark.soomjae.viewmodel.SoomjaeViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

expect val platformAppModule: Module

private val appModule = module {
    viewModelOf(::SoomjaeViewModel)
}

internal val soomjaeModule = module {
    includes(appModule, platformAppModule)

    includes(coreAnalyticsModule)
    includes(coreCommonModule)
    includes(coreDataModule)
    includes(coreImageModule)
    includes(coreRemoteModule)
    includes(corePresentationUiModule)

    includes(featuresAuthPresentationModule)
    includes(featuresAuthDataModule)
    includes(featuresHomePresentationModule)
    includes(featuresPostsAggregateDataModule)
    includes(featuresPostsAggregatePresentationModule)
    includes(featuresProfileDataModule)
    includes(featuresProfilePresentationModule)
    includes(featuresSettingPresentationModule)
}
