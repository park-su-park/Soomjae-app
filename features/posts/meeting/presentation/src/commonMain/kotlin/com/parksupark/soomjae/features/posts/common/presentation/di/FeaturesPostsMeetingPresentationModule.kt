package com.parksupark.soomjae.features.posts.common.presentation.di

import com.parksupark.soomjae.features.posts.common.presentation.tab.MeetingTabViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

private val tabModule = module {
    viewModelOf(::MeetingTabViewModel)
}

val featuresPostsMeetingPresentationModule = module {
    includes(tabModule)
}
