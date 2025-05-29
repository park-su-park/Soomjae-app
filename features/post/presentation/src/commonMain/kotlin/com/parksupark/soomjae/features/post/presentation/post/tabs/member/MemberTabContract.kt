package com.parksupark.soomjae.features.post.presentation.post.tabs.member

class MemberTabState

sealed interface MemberTabAction {
    data object OnClick : MemberTabAction
}
