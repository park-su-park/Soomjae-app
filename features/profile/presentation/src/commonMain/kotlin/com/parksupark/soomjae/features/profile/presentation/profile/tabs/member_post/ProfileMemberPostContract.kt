package com.parksupark.soomjae.features.profile.presentation.profile.tabs.member_post

data class ProfileMemberPostState(
    val isRefreshing: Boolean = true,
)

sealed interface ProfileMemberPostAction {
    data object OnClick : ProfileMemberPostAction

    data object OnPullToRefresh : ProfileMemberPostAction

    data class RefreshChange(val isRefreshing: Boolean) : ProfileMemberPostAction
}
