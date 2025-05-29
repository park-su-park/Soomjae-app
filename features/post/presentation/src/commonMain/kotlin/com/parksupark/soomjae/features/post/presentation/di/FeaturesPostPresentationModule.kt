package com.parksupark.soomjae.features.post.presentation.di

import com.parksupark.soomjae.features.post.presentation.post.PostViewModel
import com.parksupark.soomjae.features.post.presentation.post.tabs.community.CommunityTabViewModel
import com.parksupark.soomjae.features.post.presentation.post.tabs.meeting.MeetingTabViewModel
import com.parksupark.soomjae.features.post.presentation.post.tabs.member.MemberTabViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

private val postModule = module {
    viewModelOf(::PostViewModel)

    viewModelOf(::CommunityTabViewModel)
    viewModelOf(::MeetingTabViewModel)
    viewModelOf(::MemberTabViewModel)
}

val featuresPostPresentationModule = module {
    includes(postModule)
}
