package com.parksupark.soomjae.features.posts.aggregate.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.parksupark.soomjae.core.presentation.ui.navigation.NavigationDestination
import com.parksupark.soomjae.features.posts.aggregate.presentation.post.PostRoute
import com.parksupark.soomjae.features.posts.common.presentation.navigation.PostNavigator
import com.parksupark.soomjae.features.posts.community.presentation.detail.CommunityDetailRoute
import com.parksupark.soomjae.features.posts.community.presentation.write.CommunityWriteRoute
import kotlinx.serialization.Serializable

sealed interface PostDestination : NavigationDestination {

    @Serializable
    data object Root : PostDestination

    @Serializable
    data object Post : PostDestination

    @Serializable
    data object CommunityWrite : PostDestination

    @Serializable
    data class CommunityDetail(
        val postId: Long,
    ) : PostDestination
}

fun NavGraphBuilder.postGraph(
    navigator: PostNavigator,
    bottomBar: @Composable () -> Unit,
) {
    navigation<PostDestination.Root>(
        startDestination = PostDestination.Post,
    ) {
        composable<PostDestination.Post> {
            PostRoute(navigator = navigator, bottomBar = bottomBar)
        }

        composable<PostDestination.CommunityWrite> {
            CommunityWriteRoute(navigator = navigator)
        }

        composable<PostDestination.CommunityDetail> {
            CommunityDetailRoute(navigator = navigator)
        }
    }
}

fun NavHostController.navigateToPost() {
    navigate(PostDestination.Post) {
        popUpTo(PostDestination.Root) {
            inclusive = true
        }
    }
}

fun NavHostController.navigateToCommunityWrite() {
    navigate(PostDestination.CommunityWrite)
}

fun NavHostController.navigateToCommunityDetail(postId: Long) {
    navigate(PostDestination.CommunityDetail(postId))
}
