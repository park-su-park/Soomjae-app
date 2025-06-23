package com.parksupark.soomjae.features.post.presentation.post

internal class PostState

internal sealed interface PostAction {
    data object OnClick : PostAction

    data object NavigateToCommunityWrite : PostAction
}
