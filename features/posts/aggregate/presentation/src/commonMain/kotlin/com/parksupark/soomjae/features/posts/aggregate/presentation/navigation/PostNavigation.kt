package com.parksupark.soomjae.features.posts.aggregate.presentation.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.parksupark.soomjae.core.presentation.ui.navigation.NavigationDestination
import com.parksupark.soomjae.features.posts.aggregate.presentation.post.PostRoute
import com.parksupark.soomjae.features.posts.community.presentation.detail.CommunityDetailRoute
import com.parksupark.soomjae.features.posts.community.presentation.navigation.CommunityDestination
import com.parksupark.soomjae.features.posts.community.presentation.write.CommunityWriteRoute
import com.parksupark.soomjae.features.posts.meeting.presentation.detail.MeetingDetailRoute
import com.parksupark.soomjae.features.posts.meeting.presentation.detail.MeetingDetailViewModel
import com.parksupark.soomjae.features.posts.meeting.presentation.detail.rememberMeetingDetailCoordinator
import com.parksupark.soomjae.features.posts.meeting.presentation.navigation.MeetingDestination
import com.parksupark.soomjae.features.posts.meeting.presentation.participant_list.ParticipantListRoute
import com.parksupark.soomjae.features.posts.meeting.presentation.participant_list.rememberParticipantListCoordinator
import com.parksupark.soomjae.features.posts.meeting.presentation.write.MeetingWriteRoute
import com.parksupark.soomjae.features.posts.member.presentation.navigation.MemberDestination
import com.parksupark.soomjae.features.posts.member.presentation.post_write.MemberPostWriteRoute
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

sealed interface PostDestination : NavigationDestination {

    @Serializable
    data object Root : PostDestination

    @Serializable
    data object Post : PostDestination

    @Serializable
    data class MeetingDetail(val postId: Long) : PostDestination

    @Serializable
    data class MeetingParticipantList(val meetingId: Long) : PostDestination
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

        composable<CommunityDestination.CommunityWrite> {
            CommunityWriteRoute(navigator = navigator)
        }
        composable<CommunityDestination.CommunityDetail>(
            enterTransition = {
                slideInHorizontally(initialOffsetX = { it })
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { it })
            },
        ) {
            CommunityDetailRoute(navigator = navigator)
        }

        composable<MeetingDestination.MeetingWrite> {
            MeetingWriteRoute(
                navigator = navigator,
            )
        }
        composable<PostDestination.MeetingDetail>(
            enterTransition = {
                slideInHorizontally(initialOffsetX = { it })
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { it })
            },
        ) {
            val postId = it.toRoute<PostDestination.MeetingDetail>().postId
            MeetingDetailRoute(
                navigator = navigator,
                coordinator = rememberMeetingDetailCoordinator(
                    navigator = navigator,
                    viewModel = koinViewModel<MeetingDetailViewModel> {
                        parametersOf(postId)
                    },
                ),
            )
        }
        composable<PostDestination.MeetingParticipantList>(
            enterTransition = {
                slideInVertically(initialOffsetY = { it }) + fadeIn()
            },
            exitTransition = {
                slideOutVertically(targetOffsetY = { it }) + fadeOut()
            },
        ) {
            val meetingId = it.toRoute<PostDestination.MeetingParticipantList>().meetingId
            ParticipantListRoute(
                navigator = navigator,
                coordinator = rememberParticipantListCoordinator(
                    navigator = navigator,
                    viewModel = koinViewModel {
                        parametersOf(meetingId)
                    },
                ),
            )
        }

        composable<MemberDestination.MemberPostWrite> {
            MemberPostWriteRoute(
                navigator = navigator,
            )
        }
    }
}

fun NavHostController.navigateToCommunityDetail(postId: Long) {
    navigate(CommunityDestination.CommunityDetail(postId)) {
        popUpTo<PostDestination.Post> {
            inclusive = false
        }
        launchSingleTop = true
    }
}

fun NavHostController.navigateToMeetingWrite() {
    navigate(MeetingDestination.MeetingWrite())
}

fun NavHostController.navigateToMeetingPostEdit(postId: Long) {
    navigate(MeetingDestination.MeetingWrite(postId))
}

fun NavHostController.navigateToMeetingDetail(postId: Long) {
    navigate(PostDestination.MeetingDetail(postId)) {
        popUpTo<PostDestination.Post> {
            inclusive = false
        }
        launchSingleTop = true
    }
}

fun NavHostController.navigateToParticipantList(meetingId: Long) {
    navigate(PostDestination.MeetingParticipantList(meetingId)) {
        launchSingleTop = true
    }
}

fun NavHostController.navigateToMemberWrite() {
    navigate(MemberDestination.MemberPostWrite) {
        launchSingleTop = true
    }
}
