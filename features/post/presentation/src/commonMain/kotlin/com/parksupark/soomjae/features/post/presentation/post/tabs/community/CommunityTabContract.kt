package com.parksupark.soomjae.features.post.presentation.post.tabs.community

class CommunityTabState

sealed interface CommunityTabAction {
    data object OnClick : CommunityTabAction
}
