package com.parksupark.soomjae.features.posts.community.presentation.navigation

import androidx.navigation.NavHostController
import com.parksupark.soomjae.core.presentation.ui.navigation.SoomjaeNavigator
import com.parksupark.soomjae.core.presentation.ui.navigation.overridden

interface CommunityNavigator : SoomjaeNavigator {
    fun navigateToCommunityCreate()

    fun navigateToCommunityEdit(postId: Long)

    fun navigateToCommunityDetail(postId: Long)
}

private class SoomjaeCommunityNavigator(
    override val navController: NavHostController,
) : CommunityNavigator {
    override fun navigateBack() {
        overridden()
    }

    override fun navigateToCommunityCreate() {
        overridden()
    }

    override fun navigateToCommunityEdit(postId: Long) {
        overridden()
    }

    override fun navigateToCommunityDetail(postId: Long) {
        overridden()
    }
}

fun soomjaeCommunityNavigator(navController: NavHostController): CommunityNavigator =
    SoomjaeCommunityNavigator(navController)
