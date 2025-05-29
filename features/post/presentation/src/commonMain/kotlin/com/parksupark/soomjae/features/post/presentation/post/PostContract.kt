package com.parksupark.soomjae.features.post.presentation.post

class PostState

sealed interface PostAction {
    data object OnClick : PostAction
}
