package com.parksupark.soomjae.features.posts.meeting.presentation.di

import com.parksupark.soomjae.features.posts.meeting.presentation.meetingcreate.MeetingCreateViewModel
import com.parksupark.soomjae.features.posts.meeting.presentation.tab.MeetingTabViewModel
import com.parksupark.soomjae.features.posts.meeting.presentation.write.MeetingWriteViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

private val meetingCreateModule = module {
    viewModelOf(::MeetingCreateViewModel)
}

private val tabModule = module {
    viewModelOf(::MeetingTabViewModel)
}

private val writeModule = module {
    viewModelOf(::MeetingWriteViewModel)
}

val featuresPostsMeetingPresentationModule = module {
    includes(meetingCreateModule, tabModule, writeModule)
}
