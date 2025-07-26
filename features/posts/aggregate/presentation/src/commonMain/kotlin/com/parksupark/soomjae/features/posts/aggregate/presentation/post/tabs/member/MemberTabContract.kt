package com.parksupark.soomjae.features.posts.aggregate.presentation.post.tabs.member

class MemberTabState

sealed interface MemberTabAction {
    data object OnClick : MemberTabAction
}
