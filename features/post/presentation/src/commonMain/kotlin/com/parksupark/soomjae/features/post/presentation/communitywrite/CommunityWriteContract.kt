package com.parksupark.soomjae.features.post.presentation.communitywrite

class CommunityWriteState

sealed interface CommunityWriteAction {
    data object OnClick : CommunityWriteAction
}
