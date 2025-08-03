package com.parksupark.soomjae.features.posts.common.presentation.write

internal class MeetingWriteState

internal sealed interface MeetingWriteAction {
    data object OnBackClick : MeetingWriteAction
}
