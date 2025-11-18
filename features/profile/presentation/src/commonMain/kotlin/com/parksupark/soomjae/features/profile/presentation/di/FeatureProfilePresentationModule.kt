package com.parksupark.soomjae.features.profile.presentation.di

import com.parksupark.soomjae.features.profile.presentation.profile.ProfileViewModel
import com.parksupark.soomjae.features.profile.presentation.profile.tabs.member_post.ProfileMemberPostViewModel
import com.parksupark.soomjae.features.profile.presentation.profile_edit.ProfileEditViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

private val profileModule = module {
    viewModelOf(::ProfileViewModel)
}

private val profileMemberPostModule = module {
    viewModelOf(::ProfileMemberPostViewModel)
}

private val profileEditModule = module {
    viewModelOf(::ProfileEditViewModel)
}

val featuresProfilePresentationModule = module {
    includes(profileModule, profileMemberPostModule, profileEditModule)
}
