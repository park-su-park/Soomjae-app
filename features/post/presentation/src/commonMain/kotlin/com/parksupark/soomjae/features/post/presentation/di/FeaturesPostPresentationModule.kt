package com.parksupark.soomjae.features.post.presentation.di

import com.parksupark.soomjae.features.post.presentation.communitywrite.CommunityWriteViewModel
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

private val communityWriteModule = module {
    viewModelOf(::CommunityWriteViewModel)
}

val featuresPostPresentationModule = module {
    includes(postModule, communityWriteModule)
}
