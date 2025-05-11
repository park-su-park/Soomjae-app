package com.parksupark.soomjae.di

import com.parksupark.soomjae.core.common.di.coreCommonModule
import com.parksupark.soomjae.core.remote.di.coreRemoteModule
import com.parksupark.soomjae.features.auth.presentation.di.featuresAuthPresentationModule
import com.parksupark.soomjae.features.home.presentation.di.featuresHomePresentationModule
import org.koin.dsl.module

internal val soomjaeModule = module {
    includes(coreCommonModule)
    includes(coreRemoteModule)

    includes(featuresAuthPresentationModule)
    includes(featuresHomePresentationModule)
}
