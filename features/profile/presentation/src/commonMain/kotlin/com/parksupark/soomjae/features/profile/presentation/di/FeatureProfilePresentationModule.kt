package com.parksupark.soomjae.features.profile.presentation.di

import com.parksupark.soomjae.features.profile.presentation.profile.ProfileViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

private val profileModule = module {
    viewModelOf(::ProfileViewModel)
}

val featuresProfilePresentationModule = module {
    includes(profileModule)
}
