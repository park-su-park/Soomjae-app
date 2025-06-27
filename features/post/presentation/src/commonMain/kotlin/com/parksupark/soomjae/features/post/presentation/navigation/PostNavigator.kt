package com.parksupark.soomjae.features.post.presentation.navigation

import androidx.navigation.NavHostController
import com.parksupark.soomjae.core.presentation.ui.navigation.SoomjaeNavigator
import com.parksupark.soomjae.core.presentation.ui.navigation.overridden

interface PostNavigator : SoomjaeNavigator {
    fun navigateToCommunityWrite()

    fun navigateToCommunityDetail(postId: Long)
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
