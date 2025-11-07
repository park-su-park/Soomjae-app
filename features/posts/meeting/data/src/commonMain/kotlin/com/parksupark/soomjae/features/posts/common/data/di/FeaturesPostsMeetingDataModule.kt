package com.parksupark.soomjae.features.posts.common.data.di

import com.parksupark.soomjae.features.posts.common.data.repository.DefaultMeetingCommentRepository
import com.parksupark.soomjae.features.posts.common.data.repository.DefaultMeetingLikeRepository
import com.parksupark.soomjae.features.posts.common.data.repository.DefaultMeetingPostRepository
import com.parksupark.soomjae.features.posts.common.data.repository.DefaultParticipationRepository
import com.parksupark.soomjae.features.posts.common.domain.repositories.CommentRepository
import com.parksupark.soomjae.features.posts.common.domain.repositories.LikeRepository
import com.parksupark.soomjae.features.posts.common.domain.repositories.MEETING_COMMENT_REPOSITORY
import com.parksupark.soomjae.features.posts.common.domain.repositories.MEETING_LIKE_REPOSITORY
import com.parksupark.soomjae.features.posts.common.domain.repositories.MeetingPostRepository
import com.parksupark.soomjae.features.posts.common.domain.repositories.ParticipationRepository
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val featuresPostsMeetingDataModule = module {
    single {
        DefaultMeetingPostRepository(
            logger = get(),
            remoteSource = get(),
        )
    }.bind(MeetingPostRepository::class)
    single(named(MEETING_COMMENT_REPOSITORY)) {
        DefaultMeetingCommentRepository(
            httpClient = get(),
        )
    }.bind<CommentRepository>()
    single(named(MEETING_LIKE_REPOSITORY)) {
        DefaultMeetingLikeRepository(
            httpClient = get(),
        )
    }.bind<LikeRepository>()
    singleOf(::DefaultParticipationRepository).bind<ParticipationRepository>()
}
