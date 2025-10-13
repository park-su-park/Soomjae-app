package com.parksupark.soomjae.features.profile.data.di

import com.parksupark.soomjae.features.profile.data.repositories.DefaultProfileMemberPostRepository
import com.parksupark.soomjae.features.profile.domain.repositores.ProfileMemberPostRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private val profileMemberPostModule = module {
    singleOf(::DefaultProfileMemberPostRepository).bind<ProfileMemberPostRepository>()
}

val featuresProfileDataModule = module {
    includes(profileMemberPostModule)
}
