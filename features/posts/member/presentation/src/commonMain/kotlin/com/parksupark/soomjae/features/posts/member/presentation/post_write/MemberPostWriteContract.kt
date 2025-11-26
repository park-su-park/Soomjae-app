package com.parksupark.soomjae.features.posts.member.presentation.post_write

import com.parksupark.soomjae.features.posts.member.presentation.post_write.photo_picker.PhotoPickerState
import com.parksupark.soomjae.features.posts.member.presentation.post_write.post_compose.PostComposeEvent
import com.parksupark.soomjae.features.posts.member.presentation.post_write.post_compose.PostComposeState
import com.parksupark.soomjae.features.posts.member.presentation.post_write.post_write.PostWriteState

data class MemberPostWriteState(
    val postWriteState: PostWriteState = PostWriteState(),
    val photoPickerState: PhotoPickerState = PhotoPickerState(),
    val postComposeState: PostComposeState = PostComposeState(),
)

enum class MemberPostWriteScreenType {
    PHOTO_PICKER,
    POST_COMPOSE,
}

interface MemberPostWriteAction

sealed interface MemberPostWriteEvent {
    data class FromPostComposeEvent(val event: PostComposeEvent) : MemberPostWriteEvent
}
