package com.parksupark.soomjae.features.post.presentation.communitywrite

class CommunityWriteState

sealed interface CommunityWriteAction {
    data object OnBackClick : CommunityWriteAction

    data object OnConfirmClick : CommunityWriteAction
}
