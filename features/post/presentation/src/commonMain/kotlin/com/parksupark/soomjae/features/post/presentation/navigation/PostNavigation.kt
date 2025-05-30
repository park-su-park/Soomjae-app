package com.parksupark.soomjae.features.post.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.parksupark.soomjae.core.presentation.ui.navigation.NavigationDestination
import com.parksupark.soomjae.features.post.presentation.communitywrite.CommunityWriteRoute
import com.parksupark.soomjae.features.post.presentation.post.PostRoute
import kotlinx.serialization.Serializable

sealed interface PostDestination : NavigationDestination {

    @Serializable
    data object Root : PostDestination

    @Serializable
    data object Post : PostDestination

    @Serializable
    data object CommunityWrite : PostDestination
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
