package com.parksupark.soomjae.features.profile.presentation.profile.tabs.introduction

typealias HtmlContent = String

data class IntroductionState(
    val isRefreshing: Boolean = true,
    val isMe: Boolean = false,
    val content: HtmlContent? = null,
)

sealed interface IntroductionAction {
    data object OnPullToRefresh : IntroductionAction

    data object OnEditClick : IntroductionAction
}
