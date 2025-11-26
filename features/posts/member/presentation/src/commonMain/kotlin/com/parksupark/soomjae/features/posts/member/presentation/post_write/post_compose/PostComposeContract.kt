package com.parksupark.soomjae.features.posts.member.presentation.post_write.post_compose

import androidx.compose.foundation.text.input.TextFieldState
import com.parksupark.soomjae.core.image.presentation.model.PhotoUploadItem
import com.parksupark.soomjae.features.posts.member.presentation.post_write.MemberPostWriteAction
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class PostComposeState(
    val inputContent: TextFieldState = TextFieldState(),
    val photos: ImmutableList<PhotoUploadItem> = persistentListOf(),
    val isSubmitting: Boolean = false,
    val canSubmit: Boolean = false,
)

sealed interface PostComposeAction : MemberPostWriteAction {
    data object OnBackClick : PostComposeAction

    data object OnSubmitClick : PostComposeAction
}

sealed interface PostComposeEvent {
    data object OnPostUploadSuccess : PostComposeEvent
}
