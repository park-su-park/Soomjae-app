package com.parksupark.soomjae.features.profile.presentation.di

import com.parksupark.soomjae.features.profile.presentation.introduction_edit.IntroductionEditViewModel
import com.parksupark.soomjae.features.profile.presentation.profile.ProfileViewModel
import com.parksupark.soomjae.features.profile.presentation.profile.tabs.introduction.IntroductionViewModel
import com.parksupark.soomjae.features.profile.presentation.profile.tabs.member_post.ProfileMemberPostViewModel
import com.parksupark.soomjae.features.profile.presentation.profile_edit.ProfileEditViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

private val profileModule = module {
    viewModelOf(::ProfileViewModel)
}

private val profileIntroductionModule = module {
    viewModelOf(::IntroductionViewModel)
}

private val profileMemberPostModule = module {
    viewModelOf(::ProfileMemberPostViewModel)
}

private val profileEditModule = module {
    viewModelOf(::ProfileEditViewModel)
}

private val introductionEditModule = module {
    viewModelOf(::IntroductionEditViewModel)
}

val featuresProfilePresentationModule = module {
    includes(
        profileModule,
        profileIntroductionModule,
        profileMemberPostModule,
        profileEditModule,
        introductionEditModule,
    )
}
