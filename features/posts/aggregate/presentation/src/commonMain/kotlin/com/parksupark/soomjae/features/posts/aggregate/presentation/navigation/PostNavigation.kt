package com.parksupark.soomjae.features.posts.aggregate.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.parksupark.soomjae.core.presentation.ui.navigation.NavigationDestination
import com.parksupark.soomjae.features.posts.aggregate.presentation.post.PostRoute
import com.parksupark.soomjae.features.posts.community.presentation.detail.CommunityDetailRoute
import com.parksupark.soomjae.features.posts.community.presentation.write.CommunityWriteRoute
import com.parksupark.soomjae.features.posts.meeting.presentation.detail.MeetingDetailRoute
import com.parksupark.soomjae.features.posts.meeting.presentation.meetingcreate.MeetingCreateRoute
import com.parksupark.soomjae.features.posts.meeting.presentation.meetingcreate.rememberMeetingCreateCoordinator
import com.parksupark.soomjae.features.posts.meeting.presentation.write.MeetingWriteRoute
import com.parksupark.soomjae.features.posts.meeting.presentation.write.MeetingWriteViewModel
import com.parksupark.soomjae.features.posts.meeting.presentation.write.rememberMeetingWriteCoordinator
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.sharedKoinViewModel

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

    @Serializable
    data object MeetingWrite : PostDestination

    @Serializable
    data object MeetingCreate : PostDestination

    @Serializable
    data class MeetingDetail(val postId: Long) : PostDestination
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

        composable<PostDestination.MeetingWrite> {
            val viewModel = it.sharedKoinViewModel<MeetingWriteViewModel>(navigator.navController)
            MeetingWriteRoute(
                navigator = navigator,
                coordinator = rememberMeetingWriteCoordinator(navigator, viewModel),
            )
        }
        composable<PostDestination.MeetingCreate> {
            val viewModel = it.sharedKoinViewModel<MeetingWriteViewModel>(navigator.navController)
            MeetingCreateRoute(
                navigator = navigator,
                coordinator = rememberMeetingCreateCoordinator(navigator, viewModel),
            )
        }
        composable<PostDestination.MeetingDetail> {
            MeetingDetailRoute(navigator = navigator)
        }
    }
}

fun NavHostController.navigateToCommunityWrite() {
    navigate(PostDestination.CommunityWrite)
}

fun NavHostController.navigateToCommunityDetail(postId: Long) {
    navigate(PostDestination.CommunityDetail(postId))
}

fun NavHostController.navigateToMeetingWrite() {
    navigate(PostDestination.MeetingWrite)
}

fun NavHostController.navigateToMeetingCreate() {
    navigate(PostDestination.MeetingCreate)
}

fun NavHostController.navigateToMeetingDetail(postId: Long) {
    navigate(PostDestination.MeetingDetail(postId))
}
