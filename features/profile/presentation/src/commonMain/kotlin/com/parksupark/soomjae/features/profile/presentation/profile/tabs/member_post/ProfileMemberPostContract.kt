package com.parksupark.soomjae.features.profile.presentation.profile.tabs.member_post

class ProfileMemberPostState

sealed interface ProfileMemberPostAction {
    data object OnClick : ProfileMemberPostAction
}
