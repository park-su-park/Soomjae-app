package com.parksupark.soomjae.features.posts.member.presentation.di

import com.parksupark.soomjae.features.posts.member.domain.repositories.MEMBER_COMMENT_REPOSITORY
import com.parksupark.soomjae.features.posts.member.presentation.post_list.MemberPostListViewModel
import com.parksupark.soomjae.features.posts.member.presentation.post_list.comment.MemberPostCommentViewModel
import com.parksupark.soomjae.features.posts.member.presentation.post_write.MemberPostWriteCoordinator
import com.parksupark.soomjae.features.posts.member.presentation.post_write.photo_picker.PhotoPickerViewModel
import com.parksupark.soomjae.features.posts.member.presentation.post_write.post_compose.PostComposeViewModel
import com.parksupark.soomjae.features.posts.member.presentation.post_write.post_write.PostWriteViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

private val memberPostListModule = module {
    viewModelOf(::MemberPostListViewModel)
    viewModel {
        MemberPostCommentViewModel(
            dispatcher = get(),
            soomjaeEventController = get(),
            commentRepository = get(named(MEMBER_COMMENT_REPOSITORY)),
            createCommentUseCase = get(),
        )
    }
}

private val memberPostWriteModule = module {
    viewModelOf(::PhotoPickerViewModel)
    viewModelOf(::PostComposeViewModel)
    viewModelOf(::PostWriteViewModel)
    viewModelOf(::MemberPostWriteCoordinator)
}

val featuresPostsMemberPresentationModule = module {
    includes(memberPostListModule, memberPostWriteModule)
}
