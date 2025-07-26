package com.parksupark.soomjae.features.posts.common.presentation.navigation

import com.parksupark.soomjae.core.presentation.ui.navigation.SoomjaeNavigator

interface PostNavigator : SoomjaeNavigator {
    fun navigateToCommunityWrite()

    fun navigateToCommunityDetail(postId: Long)
}
