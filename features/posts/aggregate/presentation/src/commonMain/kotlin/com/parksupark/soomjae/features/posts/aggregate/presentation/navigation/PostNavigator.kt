package com.parksupark.soomjae.features.posts.aggregate.presentation.navigation

import androidx.navigation.NavHostController
import com.parksupark.soomjae.core.presentation.ui.navigation.SoomjaeNavigator
import com.parksupark.soomjae.core.presentation.ui.navigation.overridden
import com.parksupark.soomjae.features.posts.community.presentation.navigation.CommunityNavigator

interface PostNavigator : SoomjaeNavigator, CommunityNavigator {
    override fun navigateToCommunityWrite()

    override fun navigateToCommunityDetail(postId: Long)
}

private class SoomjaePostNavigator(override val navController: NavHostController) : PostNavigator {

    override fun navigateBack() {
        overridden()
    }

    override fun navigateToCommunityWrite() {
        overridden()
    }

    override fun navigateToCommunityDetail(postId: Long) {
        overridden()
    }
}

fun soomjaePostNavigator(navController: NavHostController): PostNavigator = SoomjaePostNavigator(navController)
