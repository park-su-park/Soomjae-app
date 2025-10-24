package com.parksupark.soomjae.features.posts.community.presentation.navigation

import com.parksupark.soomjae.core.presentation.ui.navigation.NavigationDestination
import kotlinx.serialization.Serializable

sealed interface CommunityDestination : NavigationDestination {
    @Serializable
    data class CommunityDetail(
        val postID: Long,
    ) : CommunityDestination
}
