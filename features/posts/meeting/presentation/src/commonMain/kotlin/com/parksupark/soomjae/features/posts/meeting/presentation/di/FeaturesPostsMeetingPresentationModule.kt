package com.parksupark.soomjae.features.posts.meeting.presentation.di

import com.parksupark.soomjae.features.posts.common.domain.repositories.MEETING_COMMENT_REPOSITORY
import com.parksupark.soomjae.features.posts.common.domain.repositories.MEETING_LIKE_REPOSITORY
import com.parksupark.soomjae.features.posts.meeting.presentation.detail.MeetingDetailViewModel
import com.parksupark.soomjae.features.posts.meeting.presentation.participant_list.ParticipantListViewModel
import com.parksupark.soomjae.features.posts.meeting.presentation.tab.MeetingTabViewModel
import com.parksupark.soomjae.features.posts.meeting.presentation.write.MeetingPostWriteCoordinator
import com.parksupark.soomjae.features.posts.meeting.presentation.write.step.MeetingPostWriteStepViewModel
import com.parksupark.soomjae.features.posts.meeting.presentation.write.post_content.MeetingPostContentViewModel
import com.parksupark.soomjae.features.posts.meeting.presentation.write.creation.MeetingCreationViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

private val detailModule = module {
    viewModel { params ->
        MeetingDetailViewModel(
            dispatcher = get(),
            meetingPostRepository = get(),
            commentRepository = get(named(MEETING_COMMENT_REPOSITORY)),
            likeRepository = get(named(MEETING_LIKE_REPOSITORY)),
            postId = params.get(),
            participationRepository = get(),
        )
    }
}

private val participantListModule = module {
    viewModel { params ->
        ParticipantListViewModel(
            meetingId = params.get(),
            participationRepository = get(),
        )
    }
}

private val tabModule = module {
    viewModelOf(::MeetingTabViewModel)
}

private val writeModule = module {
    viewModelOf(::MeetingPostWriteStepViewModel)
    viewModelOf(::MeetingCreationViewModel)
    viewModelOf(::MeetingPostContentViewModel)
    viewModelOf(::MeetingPostWriteCoordinator)
}

val featuresPostsMeetingPresentationModule = module {
    includes(detailModule, participantListModule, tabModule, writeModule)
}
