package com.parksupark.soomjae.features.post.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.parksupark.soomjae.core.presentation.ui.navigation.NavigationDestination
import com.parksupark.soomjae.features.post.presentation.post.PostRoute
import kotlinx.serialization.Serializable

sealed interface PostDestination : NavigationDestination {

    @Serializable
    data object Root : PostDestination

    @Serializable
    data object Post : PostDestination
}

fun NavGraphBuilder.postGraph() {
    navigation<PostDestination.Root>(
        startDestination = PostDestination.Post,
    ) {
        composable<PostDestination.Post> {
            PostRoute()
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
