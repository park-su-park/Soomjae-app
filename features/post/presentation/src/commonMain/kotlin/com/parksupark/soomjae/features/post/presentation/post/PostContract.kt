package com.parksupark.soomjae.features.post.presentation.post

internal class PostState

internal sealed interface PostAction {
    data object OnClick : PostAction

    data class NavigateToCommunityDetail(val postId: Long) : PostAction

    data object NavigateToCommunityWrite : PostAction
}
