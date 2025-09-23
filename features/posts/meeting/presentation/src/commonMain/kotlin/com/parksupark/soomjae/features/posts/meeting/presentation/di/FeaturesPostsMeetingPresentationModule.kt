package com.parksupark.soomjae.features.posts.meeting.presentation.di

import com.parksupark.soomjae.features.posts.common.domain.repositories.MEETING_LIKE_REPOSITORY
import com.parksupark.soomjae.features.posts.meeting.presentation.detail.MeetingDetailViewModel
import com.parksupark.soomjae.features.posts.meeting.presentation.tab.MeetingTabViewModel
import com.parksupark.soomjae.features.posts.meeting.presentation.write.MeetingWriteViewModel
import org.koin.core.module.dsl.named
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

private val detailModule = module {
    viewModelOf(::MeetingDetailViewModel) { named(MEETING_LIKE_REPOSITORY) }
}

private val tabModule = module {
    viewModelOf(::MeetingTabViewModel)
}

private val writeModule = module {
    viewModelOf(::MeetingWriteViewModel)
}

val featuresPostsMeetingPresentationModule = module {
    includes(detailModule, tabModule, writeModule)
}
