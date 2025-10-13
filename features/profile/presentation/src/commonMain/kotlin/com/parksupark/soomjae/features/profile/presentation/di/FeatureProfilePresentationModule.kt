package com.parksupark.soomjae.features.profile.presentation.di

import com.parksupark.soomjae.features.profile.presentation.profile.ProfileViewModel
import com.parksupark.soomjae.features.profile.presentation.profile.tabs.member_post.ProfileMemberPostViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

private val profileModule = module {
    viewModelOf(::ProfileViewModel)
}

private val profileMemberPostModule = module {
    viewModelOf(::ProfileMemberPostViewModel)
}

val featuresProfilePresentationModule = module {
    includes(profileModule, profileMemberPostModule)
}
