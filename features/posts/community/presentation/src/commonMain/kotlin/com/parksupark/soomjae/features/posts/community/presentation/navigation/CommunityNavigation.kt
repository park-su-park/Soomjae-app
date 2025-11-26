package com.parksupark.soomjae.features.posts.community.presentation.navigation

import androidx.navigation.NavHostController
import com.parksupark.soomjae.core.presentation.ui.navigation.NavigationDestination
import kotlinx.serialization.Serializable

sealed interface CommunityDestination : NavigationDestination {
    @Serializable
    data class CommunityDetail(
        val postID: Long,
    ) : CommunityDestination

    @Serializable
    data class CommunityWrite(
        val postID: Long? = null,
    ) : CommunityDestination
}

fun NavHostController.navigateToCommunityCreate() {
    navigate(CommunityDestination.CommunityWrite(null))
}

fun NavHostController.navigateToCommunityEdit(postId: Long) {
    navigate(CommunityDestination.CommunityWrite(postId))
}
