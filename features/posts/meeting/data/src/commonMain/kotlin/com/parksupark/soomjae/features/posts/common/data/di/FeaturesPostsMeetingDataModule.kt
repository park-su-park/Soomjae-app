package com.parksupark.soomjae.features.posts.common.data.di

import com.parksupark.soomjae.features.posts.common.data.repositories.DefaultMeetingCommentRepository
import com.parksupark.soomjae.features.posts.common.data.repositories.DefaultMeetingLikeRepository
import com.parksupark.soomjae.features.posts.common.data.repositories.DefaultMeetingPostRepository
import com.parksupark.soomjae.features.posts.common.data.repositories.DefaultParticipationRepository
import com.parksupark.soomjae.features.posts.common.domain.repositories.CommentRepository
import com.parksupark.soomjae.features.posts.common.domain.repositories.LikeRepository
import com.parksupark.soomjae.features.posts.common.domain.repositories.MEETING_COMMENT_REPOSITORY
import com.parksupark.soomjae.features.posts.common.domain.repositories.MEETING_LIKE_REPOSITORY
import com.parksupark.soomjae.features.posts.common.domain.repositories.MeetingPostRepository
import com.parksupark.soomjae.features.posts.common.domain.repositories.ParticipationRepository
import org.koin.core.module.dsl.named
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val featuresPostsMeetingDataModule = module {
    singleOf(::DefaultMeetingPostRepository).bind(MeetingPostRepository::class)
    singleOf(::DefaultMeetingCommentRepository) { named(MEETING_COMMENT_REPOSITORY) }.bind<CommentRepository>()
    singleOf(::DefaultMeetingLikeRepository) { named(MEETING_LIKE_REPOSITORY) }.bind<LikeRepository>()
    singleOf(::DefaultParticipationRepository).bind<ParticipationRepository>()
}
