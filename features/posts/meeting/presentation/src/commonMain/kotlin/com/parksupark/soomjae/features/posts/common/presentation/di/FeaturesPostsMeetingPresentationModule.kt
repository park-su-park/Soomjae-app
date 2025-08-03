package com.parksupark.soomjae.features.posts.common.presentation.di

import com.parksupark.soomjae.features.posts.common.presentation.tab.MeetingTabViewModel
import com.parksupark.soomjae.features.posts.common.presentation.write.MeetingWriteViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

private val tabModule = module {
    viewModelOf(::MeetingTabViewModel)
}

private val writeModule = module {
    viewModelOf(::MeetingWriteViewModel)
}

val featuresPostsMeetingPresentationModule = module {
    includes(tabModule, writeModule)
}
