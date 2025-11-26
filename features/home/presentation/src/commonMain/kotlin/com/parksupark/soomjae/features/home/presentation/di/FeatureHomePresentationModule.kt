package com.parksupark.soomjae.features.home.presentation.di

import com.parksupark.soomjae.features.home.presentation.home.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

private val homeModule = module {
    viewModelOf(::HomeViewModel)
}

val featuresHomePresentationModule = module {
    includes(homeModule)
}
