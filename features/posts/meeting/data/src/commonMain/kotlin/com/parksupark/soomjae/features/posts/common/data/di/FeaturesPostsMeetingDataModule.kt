package com.parksupark.soomjae.features.posts.common.data.di

import com.parksupark.soomjae.features.posts.common.data.repositories.DefaultMeetingPostRepository
import com.parksupark.soomjae.features.posts.common.domain.repositories.MeetingPostRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val featuresPostsMeetingDataModule = module {
    singleOf(::DefaultMeetingPostRepository).bind(MeetingPostRepository::class)
}
