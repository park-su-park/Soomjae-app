package com.parksupark.soomjae.features.posts.meeting.presentation.di

import com.parksupark.soomjae.features.posts.common.domain.repositories.MEETING_COMMENT_REPOSITORY
import com.parksupark.soomjae.features.posts.common.domain.repositories.MEETING_LIKE_REPOSITORY
import com.parksupark.soomjae.features.posts.common.presentation.PostAction
import com.parksupark.soomjae.features.posts.meeting.presentation.detail.MeetingDetailViewModel
import com.parksupark.soomjae.features.posts.meeting.presentation.participant_list.ParticipantListViewModel
import com.parksupark.soomjae.features.posts.meeting.presentation.tab.MeetingTabCoordinator
import com.parksupark.soomjae.features.posts.meeting.presentation.tab.filter.MeetingTabFilterViewModel
import com.parksupark.soomjae.features.posts.meeting.presentation.tab.post.MeetingTabPostViewModel
import com.parksupark.soomjae.features.posts.meeting.presentation.write.post_content.MeetingPostContentViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

private val detailModule = module {
    viewModel { params ->
        MeetingDetailViewModel(
            postId = params.get(),
            dispatcher = get(),
            sessionRepository = get(),
            meetingPostRepository = get(),
            commentRepository = get(named(MEETING_COMMENT_REPOSITORY)),
            likeRepository = get(named(MEETING_LIKE_REPOSITORY)),
            participationRepository = get(),
            controller = get(),
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
    viewModel {
        MeetingTabFilterViewModel(
            categoryRepository = get(),
            locationRepository = get(),
        )
    }
    viewModel {
        MeetingTabPostViewModel(
            meetingRepository = get(),
            sessionRepository = get(),
            soomjaeEventController = get(),
        )
    }
    viewModel { (onPostAction: (PostAction) -> Unit) ->
        MeetingTabCoordinator(
            onPostAction = onPostAction,
            filterViewModel = get(),
            postViewModel = get(),
        )
    }
}

private val writeModule = module {
    viewModelOf(::MeetingPostContentViewModel)
}

val featuresPostsMeetingPresentationModule = module {
    includes(detailModule, participantListModule, tabModule, writeModule)
}
