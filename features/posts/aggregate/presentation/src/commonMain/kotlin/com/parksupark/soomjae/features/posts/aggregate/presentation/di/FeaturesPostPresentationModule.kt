package com.parksupark.soomjae.features.posts.aggregate.presentation.di

import com.parksupark.soomjae.features.posts.aggregate.presentation.post.PostViewModel
import com.parksupark.soomjae.features.posts.aggregate.presentation.post.tabs.member.MemberTabViewModel
import com.parksupark.soomjae.features.posts.common.presentation.di.featuresPostsCommonPresentationModule
import com.parksupark.soomjae.features.posts.community.presentation.di.featuresPostsCommunityPresentationModule
import com.parksupark.soomjae.features.posts.meeting.presentation.di.featuresPostsMeetingPresentationModule
import com.parksupark.soomjae.features.posts.meeting.presentation.tab.MeetingTabViewModel
import com.parksupark.soomjae.features.posts.member.presentation.di.featuresPostsMemberPresentationModule
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

private val postModule = module {
    viewModelOf(::PostViewModel)

    viewModelOf(::MeetingTabViewModel)
    viewModelOf(::MemberTabViewModel)
}

val featuresPostsAggregatePresentationModule = module {
    includes(postModule)
    includes(
        featuresPostsCommonPresentationModule,
        featuresPostsCommunityPresentationModule,
        featuresPostsMeetingPresentationModule,
        featuresPostsMemberPresentationModule,
    )
}
