package com.parksupark.soomjae.features.posts.common.data.di

import com.parksupark.soomjae.core.domain.post.repository.MeetingPostRepository
import com.parksupark.soomjae.features.posts.common.data.cache.MeetingPostPatchCache
import com.parksupark.soomjae.features.posts.common.data.network.datasource.RemoteMeetingLikeDataSource
import com.parksupark.soomjae.features.posts.common.data.network.datasource.RemoteMeetingPostSource
import com.parksupark.soomjae.features.posts.common.data.repository.DefaultMeetingCommentRepository
import com.parksupark.soomjae.features.posts.common.data.repository.DefaultMeetingLikeRepository
import com.parksupark.soomjae.features.posts.common.data.repository.DefaultMeetingPostRepository
import com.parksupark.soomjae.features.posts.common.data.repository.DefaultParticipationRepository
import com.parksupark.soomjae.features.posts.common.domain.repositories.CommentRepository
import com.parksupark.soomjae.features.posts.common.domain.repositories.LikeRepository
import com.parksupark.soomjae.features.posts.common.domain.repositories.MEETING_COMMENT_REPOSITORY
import com.parksupark.soomjae.features.posts.common.domain.repositories.MEETING_LIKE_REPOSITORY
import com.parksupark.soomjae.features.posts.common.domain.repositories.ParticipationRepository
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

private val meetingPostModule = module {
    single { MeetingPostPatchCache() }
    factory { RemoteMeetingPostSource(httpClient = get()) }

    single {
        DefaultMeetingPostRepository(
            logger = get(),
            patchCache = get(),
            remoteSource = get(),
            bus = get(),
        )
    }.bind(MeetingPostRepository::class)
}

private val meetingCommentModule = module {
    single(named(MEETING_COMMENT_REPOSITORY)) {
        DefaultMeetingCommentRepository(
            httpClient = get(),
        )
    }.bind<CommentRepository>()
}

private val meetingLikeModule = module {
    factory { RemoteMeetingLikeDataSource(httpClient = get()) }

    single(named(MEETING_LIKE_REPOSITORY)) {
        DefaultMeetingLikeRepository(
            postCache = get(),
            remoteDataSource = get(),
        )
    }.bind<LikeRepository>()
}

private val meetingParticipationModule = module {
    singleOf(::DefaultParticipationRepository).bind<ParticipationRepository>()
}

val featuresPostsMeetingDataModule = module {
    includes(
        meetingPostModule,
        meetingCommentModule,
        meetingLikeModule,
        meetingParticipationModule,
    )
}
