package com.parksupark.soomjae.di

import com.parksupark.soomjae.core.common.di.coreCommonModule
import com.parksupark.soomjae.core.remote.di.coreRemoteModule
import com.parksupark.soomjae.features.auth.presentation.di.featuresAuthPresentationModule
import com.parksupark.soomjae.features.home.presentation.di.featuresHomePresentationModule
import com.parksupark.soomjae.features.profile.presentation.di.featuresProfilePresentationModule
import com.parksupark.soomjae.viewmodel.SoomjaeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

private val appModule = module {
    viewModelOf(::SoomjaeViewModel)
}

internal val soomjaeModule = module {
    includes(appModule)
    includes(coreCommonModule)
    includes(coreRemoteModule)

    includes(featuresAuthPresentationModule)
    includes(featuresHomePresentationModule)
    includes(featuresProfilePresentationModule)
}
